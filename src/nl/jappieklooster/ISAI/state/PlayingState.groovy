package nl.jappieklooster.ISAI.state

import com.jme3.app.Application
import com.jme3.app.state.AbstractAppState
import com.jme3.app.state.AppStateManager
import com.jme3.input.FlyByCamera
import com.jme3.input.InputManager;
import com.jme3.input.controls.KeyTrigger
import com.jme3.input.controls.Trigger;
import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.input.KeyInput

import nl.jappieklooster.ISAI.init.LevelLoader
import nl.jappieklooster.ISAI.input.InputDirector
import nl.jappieklooster.ISAI.input.InputHandler
import nl.jappieklooster.ISAI.world.World;

import com.jme3.app.SimpleApplication

class PlayingState extends ACommenState{

	private World world
	private LevelLoader loader
	String level

	@Override
	void init(AppStateManager stateManager, SimpleApplication app) {
		loader = new LevelLoader(app.assetManager)

		app.viewPort.setBackgroundColor(new ColorRGBA(0.5f, 0.3f, 0.2f, 1f));

		FlyByCamera flyCam = ((SimpleApplication)app).flyByCamera
		flyCam.setMoveSpeed(100)
		flyCam.cam.setFrustumFar(9000)

		Random random = new Random()
		
		level = "one"
		
		InputDirector director = new InputDirector(inputManager)
		director.addHandler(
			new InputHandler(
				triggers:[
					new KeyTrigger(KeyInput.KEY_X)
				],
				handler:{float value, float tpf, String name ->
					stateManager.attach(new MenuState())
					stateManager.detach(delegate)
				}
			)
		)
		
		load()
	}
	@Override
    void setEnabled(boolean enabled) {
		super.setEnabled(enabled)
		if(enabled){
			load()
		}else{
			unload()
		}
    }
	
	private void load(){
		setUpLight()

		world = loader.loadFromFile("levels/"+level)
        rootNode.attachChild(world.node)
	}
	private void unload(){
		rootNode.detachChild(world.node)
		world = null // can be quite big, so mark it for gc
		//WARNING does not kill the thread pool under the asumption they will end automaticly
	}

	@Override
	void update(float tpf) {
		if(!enabled){
			return // I am not enabled
		}

		if(tpf > 1){ // prevent a first huge tick or any other
			return
		}
		world.update(tpf); // let the world do its thing
	}
	private void setUpLight() {
		// We add light so we see the scene
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(al);

		DirectionalLight dl = new DirectionalLight();
		dl.setColor(ColorRGBA.White);
		dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
		rootNode.addLight(dl);
	} 
	/**
	 * opositie off initialize
	 */
	@Override
	void cleanup() {
        super.cleanup();
		loader.releaseThreadPool()
		loader = null // threads can also be quite big, so mark it for gc
        //executor.shutdown();

    }
}
