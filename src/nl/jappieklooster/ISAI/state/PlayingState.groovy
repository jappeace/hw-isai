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
import nl.jappieklooster.ISAI.collection.graph.Graph;
import nl.jappieklooster.ISAI.collection.graph.Vertex;
import nl.jappieklooster.ISAI.init.LevelLoader
import nl.jappieklooster.ISAI.input.InputDirector
import nl.jappieklooster.ISAI.input.InputHandler
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.math.vector.Vector3

import com.jme3.app.SimpleApplication

class PlayingState extends AnInputDirectingState{

	World world
	

    private Graph graph = null
	@Override
	void init(Game app) {
		super.init(app)
		director.addHandler(
			new InputHandler(
				[
					new KeyTrigger(KeyInput.KEY_X)
				],
				{float value, float tpf, String name ->
					
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
		director.addHandler(
			new InputHandler(
				[
					new KeyTrigger(KeyInput.KEY_F)
				],
				{float value, float tpf, String name ->
					world.node.attachChild(world.environment.navGraph.node)
				}
			)
		)
		director.addHandler(
			new InputHandler(
				[
					new KeyTrigger(KeyInput.KEY_G)
				],
				{float value, float tpf, String name ->
                    world.node.detachChild(world.environment.navGraph.node)
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
