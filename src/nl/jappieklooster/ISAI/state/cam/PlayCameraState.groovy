package nl.jappieklooster.ISAI.state.cam

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.state.ACommenState

/**
 * this state makes the camera respond on input so that it will act like its in the playing state,
 * ie wasd for movement, shift+ wasd for tilting and ctrl + shift wasd for zooming and rotating
 * @author jappie
 *
 */
class PlayCameraState extends ACommenState{

	private MovingCamera camera

	void init(Game app){
		camera = new MovingCamera(app.camera)
	}
}
