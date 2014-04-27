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

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.LevelLoader
import nl.jappieklooster.ISAI.input.InputDirector
import nl.jappieklooster.ISAI.input.InputHandler
import nl.jappieklooster.ISAI.world.World;

import com.jme3.app.SimpleApplication

class PlayingState extends ACommenState{

	World world
	

	@Override
	void init(Game app) {

		InputDirector director = new InputDirector(inputManager)
		director.addHandler(
			new InputHandler(
				triggers:[
					new KeyTrigger(KeyInput.KEY_X)
				],
				handler:{float value, float tpf, String name ->
					
					MenuState menu = new MenuState(world: world)
                    if(stateManager.hasState(menu)){
						// the button is pressed so long that this action is executed twice or more
						return
					}
					stateManager.attach(menu)
					stateManager.detach(delegate)
				}
			)
		)
		
	}

	@Override
	void update(float tpf) {
		if(tpf > 1){ // prevent a first huge tick or any other
			return
		}
		world.update(tpf); // let the world do its thing
	}
	/**
	 * opositie off initialize
	 */
	@Override
	void cleanup() {
        super.cleanup();
        rootNode.detachChild(world.node)
        world = null // can be quite big, so mark it for gc
    }
}
