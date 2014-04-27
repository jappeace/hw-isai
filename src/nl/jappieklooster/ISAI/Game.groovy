package nl.jappieklooster.ISAI

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
import com.jme3.system.AppSettings
import de.lessvoid.nifty.Nifty

import java.awt.Dimension
import nl.jappieklooster.ISAI.init.LevelLoader
import nl.jappieklooster.ISAI.state.LoadState
import nl.jappieklooster.ISAI.state.PlayingState
import java.awt.Toolkit

class Game extends SimpleApplication {
	
	static final String gameName = "Of gods &"
	private LoadState loader
	private Nifty nifty
	Game(){

		super()
		loader = new LoadState(loader:new LevelLoader())
		showSettings = false
        settings = new AppSettings(true);
        settings.setTitle(gameName);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        settings.setWidth((int)(screenSize.getWidth() / 2));
        settings.setHeight((int)(screenSize.getHeight() - 10));
	}
	@Override
	void simpleInitApp() {
		loader.loader.assetManager = assetManager
		stateManager.attach(loader)
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort)
        nifty = niftyDisplay.getNifty();

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
	}
	
	void loadLevel(String path){
		loader.level = path
	}
	
	@Override
	void destroy() {
		super.destroy()
		loader.destroy()
	}
	Nifty getNifty(){ nifty }
}
