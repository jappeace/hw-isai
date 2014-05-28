package nl.jappieklooster.ISAI.state.global

import com.jme3.app.Application
import com.jme3.app.SimpleApplication
import com.jme3.app.state.AbstractAppState
import com.jme3.app.state.AppStateManager
import com.jme3.asset.AssetManager
import com.jme3.input.InputManager
import com.jme3.renderer.ViewPort
import com.jme3.scene.Node
import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.input.InputDirector

/**
 * manages the inputdirector creation and cleanup
 * @author jappie
 *
 */
abstract class AnInputDirectingState extends ACommenState{

	
	protected InputDirector director
	@Override
	void init(Game app){
		super.init(app)
		director = new InputDirector(inputManager)
	}

	@Override
	void cleanup() {
        super.cleanup();
		director.removeMyHandlers()
    }
}
