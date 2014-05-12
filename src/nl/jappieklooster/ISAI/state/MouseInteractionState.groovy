package nl.jappieklooster.ISAI.state

import com.jme3.input.controls.KeyTrigger
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.math.Vector3f
import com.jme3.input.MouseInput;


import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.input.InputHandler
import nl.jappieklooster.ISAI.state.ACommenState
import nl.jappieklooster.ISAI.state.AnInputDirectingState
import nl.jappieklooster.ISAI.world.entity.Entity
import nl.jappieklooster.ISAI.world.entity.tracking.ClickablesTracker

import nl.jappieklooster.math.vector.Converter
/**
 * this state makes the camera respond on input so that it will act like its in the playing state,
 * ie wasd for movement, shift+ wasd for tilting and ctrl + shift wasd for zooming and rotating
 * @author jappie
 *
 */
class MouseInteractionState extends AnInputDirectingState{
	ClickablesTracker clickTracker

	void init(Game app){
		super.init(app)
		director.addPressedHandler(new InputHandler(
			"Left",
			[
				new MouseButtonTrigger(MouseInput.BUTTON_LEFT)
			],
			{float value, float tpf, String name ->
				
				Entity clickedEntity = clickTracker.click(rootNode, inputManager.cursorPos)
				if(clickedEntity){
					clickedEntity.geometry.rotate(0, 0, 0.1)
				}
			}
		))
	}
}
