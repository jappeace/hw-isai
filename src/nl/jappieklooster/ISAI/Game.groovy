package nl.jappieklooster.ISAI

import com.jme3.app.SimpleApplication
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f

import nl.jappieklooster.ISAI.init.LevelLoader
import nl.jappieklooster.ISAI.state.PlayingState


class Game extends SimpleApplication {

	@Override
	public void simpleInitApp() {
		PlayingState state = new PlayingState()
		stateManager.attach(state)
	}
}
