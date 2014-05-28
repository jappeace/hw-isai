package nl.jappieklooster.ISAI

import com.jme3.app.Application
import com.jme3.app.DebugKeysAppState
import com.jme3.app.FlyCamAppState
import com.jme3.app.SimpleApplication
import com.jme3.app.StatsAppState
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.niftygui.NiftyJmeDisplay
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.system.AppSettings
import com.jme3.system.JmeSystem;

import de.lessvoid.nifty.Nifty

import com.jme3.app.state.AppState

import java.awt.Dimension

import nl.jappieklooster.ISAI.init.LevelLoader
import nl.jappieklooster.ISAI.init.factory.WireFrameFactory
import nl.jappieklooster.ISAI.state.global.LoadState;
import nl.jappieklooster.ISAI.state.global.MouseInteractionState;
import nl.jappieklooster.ISAI.state.global.PlayingState;
import nl.jappieklooster.ISAI.state.global.cam.PlayCameraState;

import java.awt.Toolkit

/**
 * this is my own replacement for simpleapplication, which nailed several things into the framework, I cannot cleanly work with 
 * (like the flybycam)
 * @author jappie
 *
 */
class Game extends Application {
	
	static final String gameName = "Burn it"
	private LoadState loader
	private Nifty nifty
	private Node rootNode = new Node("Root Node")
	private Node guiNode = new Node("Gui Node")
	private static final Collection<AppState> initialStates = [new DebugKeysAppState(), new PlayCameraState(), new MouseInteractionState()]

	Game(){
		super()
		initialStates.each{
			stateManager.attach(it)
		}
		loader = new LoadState(loader:new LevelLoader(this))
		stateManager.attach(loader)
	}
	
	Node getRootNode(){ rootNode }
	Nifty getNifty(){ nifty }
	@Override
	public void start() {
        settings = new AppSettings(true);

        settings.setTitle(gameName);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        settings.setWidth((int)(screenSize.getWidth() / 2));
        settings.setHeight((int)(screenSize.getHeight() - 10));

		//re-setting settings they can have been merged from the registry.
		setSettings(settings);
		super.start();
	}
	
	LoadState loadLevel(String path){
		loader.level = path
		return loader
	}
	
	@Override
	void destroy() {
		super.destroy()
		loader.destroy()
	}
	
	@Override
	void initialize() {
		super.initialize()
		guiNode.setQueueBucket(Bucket.Gui)
		guiNode.setCullHint(CullHint.Never)
		viewPort.attachScene(rootNode)
		guiViewPort.attachScene(guiNode)

		stateManager.attach(new StatsAppState(guiNode, assetManager.loadFont("Interface/Fonts/Default.fnt")))
		
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort)
        nifty = niftyDisplay.getNifty()

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay)
		
		
		// give wireframefactory acces to the assetmanager
		WireFrameFactory factory = WireFrameFactory.getInstance()
		factory.random = new Random()
		factory.setAssetManager(assetManager)
	}

	
	@Override
	public void update() {
		super.update(); // makes sure to execute AppTasks
		if (speed == 0 || paused) {
			return;
		}

		float tpf = timer.getTimePerFrame() * speed;

		// update states
		stateManager.update(tpf);

		/* custom update goes here*/

		rootNode.updateLogicalState(tpf);
		guiNode.updateLogicalState(tpf);
		
		rootNode.updateGeometricState();
		guiNode.updateGeometricState();

		// render states
		stateManager.render(renderManager);
		renderManager.render(tpf, context.isRenderable());
		
		/*custom rendering goes here*/

		stateManager.postRender();
	}
}
