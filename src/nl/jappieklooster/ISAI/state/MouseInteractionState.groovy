package nl.jappieklooster.ISAI.state

import com.jme3.input.controls.KeyTrigger
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.math.Vector3f
import com.jme3.input.MouseInput;


import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.behaviour.ISteerable
import nl.jappieklooster.ISAI.behaviour.Seek
import nl.jappieklooster.ISAI.input.InputHandler
import nl.jappieklooster.ISAI.state.ACommenState
import nl.jappieklooster.ISAI.state.AnInputDirectingState
import nl.jappieklooster.ISAI.world.entity.Entity
import nl.jappieklooster.ISAI.world.entity.Vehicle
import nl.jappieklooster.ISAI.world.entity.tracking.ClickablesTracker

import nl.jappieklooster.math.vector.Converter
import nl.jappieklooster.math.vector.Vector3
/**
 * this state makes mouse interaction possible, ie clicks and stuf
 * @author jappie
 *
 */
class MouseInteractionState extends AnInputDirectingState{
	ClickablesTracker clickTracker
	Vehicle selected

	void init(Game app){
		super.init(app)
		director.addHandler(new InputHandler(
			"Left",
			[
				new MouseButtonTrigger(MouseInput.BUTTON_LEFT)
			],
			{float value, float tpf, String name ->
				
				if(selected == null){
					selected = trySelect()
					return
				}
				Vector3 location = clickTracker.clickOnSurface(inputManager.cursorPos)
				if(location == null){
					return
				}
				selected.add(
                    new Seek(
                        getToCallback:{location},
                        onArrive:{
                            Seek self ->
                            self.entity.invalidatedBehaviours.add(self)
                        }
                    )
                )
                selected = null

			}
		))
		director.addHandler(new InputHandler(
			"Right",
			[
				new MouseButtonTrigger(MouseInput.BUTTON_RIGHT)
			],
			{float value, float tpf, String name ->
				
				Vehicle clicked = trySelect()
				if(clicked){
                    clicked.invalidatedBehaviours.addAll(clicked.steeringBehaviours)
				}
			}
		))
	}
	private Vehicle trySelect(){
		
        clickTracker.candidates = rootNode
		Entity result = clickTracker.clickOnEntity(inputManager.cursorPos)
		if(result instanceof Vehicle){
			return (Vehicle) result
		}
        return null
	}
}
