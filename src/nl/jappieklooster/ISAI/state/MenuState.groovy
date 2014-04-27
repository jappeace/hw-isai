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

        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(app.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", app.assetManager.loadTexture("Interface/Logo/Monkey.jpg"));
        geom.setMaterial(mat);
        rootNode.attachChild(geom);

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

    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void quit(){
        nifty.gotoScreen("end");
    }
}
