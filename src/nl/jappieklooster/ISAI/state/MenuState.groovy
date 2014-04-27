package nl.jappieklooster.ISAI.state

import com.jme3.app.state.AbstractAppState
import com.jme3.app.state.AppStateManager
import com.jme3.material.Material
import com.jme3.niftygui.NiftyJmeDisplay
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import de.lessvoid.nifty.Nifty
import de.lessvoid.nifty.screen.Screen
import de.lessvoid.nifty.screen.ScreenController
import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.world.World
import com.jme3.math.Vector3f
import com.jme3.input.FlyByCamera;

class MenuState extends ACommenState implements ScreenController{

	World world
    private Nifty nifty;
	private FlyByCamera cam
	@Override
	void init(Game app) {

		if(world){
			rootNode.attachChild(world.node)
		}
		nifty = app.nifty
        nifty.fromXml("Interface/Menu.xml", "start", this);


		cam = app.flyCam
        // disable the fly cam
		cam.setDragToRotate(true);
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

		cam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
		stateManager.attach(new PlayingState(world:world))
		stateManager.detach(this)
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
	@Override
	void cleanup() {
		super.cleanup()
		nifty.exit()
	}
}

