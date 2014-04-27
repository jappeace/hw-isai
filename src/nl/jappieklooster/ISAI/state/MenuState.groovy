package nl.jappieklooster.ISAI.state

import com.jme3.app.Application
import com.jme3.app.SimpleApplication
import com.jme3.app.state.AbstractAppState
import com.jme3.app.state.AppStateManager
import com.jme3.material.Material
import com.jme3.niftygui.NiftyJmeDisplay
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import de.lessvoid.nifty.Nifty
import de.lessvoid.nifty.screen.Screen
import de.lessvoid.nifty.screen.ScreenController
import com.jme3.math.Vector3f

class MenuState extends ACommenState implements ScreenController{

    private Nifty nifty;
	@Override
	void init(AppStateManager stateManager, SimpleApplication app) {

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(app.assetManager,
                                                          app.inputManager,
                                                          app.audioRenderer,
                                                          app.guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/Menu.xml", "start", this);

        // attach the nifty display to the gui view port as a processor
        app.guiViewPort.addProcessor(niftyDisplay);

        // disable the fly cam
//        flyCam.setEnabled(false);
//        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
	}

	
	@Override
	void update(float tpf){
		int x = 3;	
		x++
	}

    void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    void onStartScreen() {
        System.out.println("onStartScreen");
    }

    void onEndScreen() {
        System.out.println("onEndScreen");
    }

    void quit(){
        nifty.gotoScreen("end");
    }

	/** 
	 * this state object does not consider itself to have state,
	 * so all objects of this class are considerd equal
	 * */
	@Override
	boolean equals(Object to){
		return to instanceof MenuState
	}
	@Override
	int hashCode(){
		return this.class.hashCode()
	}
}
