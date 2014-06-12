package nl.jappieklooster.ISAI.state.global

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

class LoadState extends ACommenState{
	static final String firstLevel = "states"

	World world
	LevelLoader loader
	String level
	private boolean loaded


	@Override
	void init(Game app) {

		loaded = false

		// TODO: move to dsl
		app.cam.setFrustumFar(9000)

	}

	@Override
	void update(float tpf) {
		if(loaded){
			stateManager.attach(new PlayingState(world: world))
			stateManager.detach(this)
		}
		viewPort.setBackgroundColor(new ColorRGBA(0.5f, 0.3f, 0.2f, 1f));


		// if no level is set goto level one
		level = level ?: "levels/"+ firstLevel
		
		if(world){
			if(world.name == level){
				loadLevel()
				return
			}
			rootNode.detachChild(world.node)
		}
		world = loader.loadFromFile(level)
		loadLevel()
	}


	/**
	 * attach the worldnode to rootnode and mark this state as invalid
	 */
	private void loadLevel() {
        rootNode.attachChild(world.node)

		MouseInteractionState mouseInteraction = stateManager.getState(MouseInteractionState)
		if(mouseInteraction){
			mouseInteraction.clickTracker = loader.clickTracker
			mouseInteraction.world = world
		}
		loaded = true
	} 
	/**
	 * when called will destroy the theadpool, this gaurantees that the application will either exit or crash
	 */
	public void destroy() {
		loader.shutdownThreadPool()
	}
	
}
