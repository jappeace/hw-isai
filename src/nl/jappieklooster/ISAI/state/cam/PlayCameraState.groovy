package nl.jappieklooster.ISAI.state.cam

import com.jme3.input.controls.KeyTrigger
import com.jme3.math.Vector3f
import com.jme3.input.KeyInput;

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.input.InputHandler
import nl.jappieklooster.ISAI.state.ACommenState
import nl.jappieklooster.ISAI.state.AnInputDirectingState

/**
 * this state makes the camera respond on input so that it will act like its in the playing state,
 * ie wasd for movement, shift+ wasd for tilting and ctrl + shift wasd for zooming and rotating
 * @author jappie
 *
 */
class PlayCameraState extends AnInputDirectingState{

	private MovingCamera camera
	private Vector3f initialUpVec
	private boolean isRotating = false
	PlayCameraState(){
		initialUpVec = null
	}
	void init(Game app){
		super.init(app)
		camera = new MovingCamera(app.camera)
		if(initialUpVec == null){
            initialUpVec = app.camera.up.clone()
		}
		director.addPressedHandler(new InputHandler(
			"StartRotating",
			[
				new KeyTrigger(KeyInput.KEY_LSHIFT),
			],
			{float value, float tpf, String name ->
				if(!isRotating){
                    isRotating = true
					return
				}
				isRotating = false
			}
		))
		director.addHandler(new InputHandler(
			"Left",
			[
				new KeyTrigger(KeyInput.KEY_A)
			],
			{float value, float tpf, String name ->
				if(isRotating){
                    camera.rotate(value, initialUpVec)
					return
				}
				camera.move(value, true)
			}
		))
		director.addHandler(new InputHandler(
			"Right",
			[
				new KeyTrigger(KeyInput.KEY_D)
			],
			{float value, float tpf, String name ->
				if(isRotating){
                    camera.rotate(-value, initialUpVec)
					return
				}
				camera.move(-value, true)
			}
		))
		director.addHandler(new InputHandler(
			"Forward",
			[
				new KeyTrigger(KeyInput.KEY_W)
			],
			{float value, float tpf, String name ->
				if(isRotating){
                    camera.rotate(-value, app.camera.getLeft())
					return
				}
				camera.move(value, false)
			}
		))
		director.addHandler(new InputHandler(
			"Backward",
			[
				new KeyTrigger(KeyInput.KEY_S)
			],
			{float value, float tpf, String name ->
				if(isRotating){
                    camera.rotate(value, app.camera.getLeft())
					return
				}
				camera.move(-value, false)
			}
		))
	}
}
