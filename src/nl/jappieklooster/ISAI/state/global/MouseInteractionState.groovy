package nl.jappieklooster.ISAI.state.global

import com.jme3.input.controls.KeyTrigger
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.math.Vector3f
import com.jme3.input.MouseInput;

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.behaviour.BehaviourScheduler
import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.behaviour.Invalidator
import nl.jappieklooster.ISAI.behaviour.steer.Seek;
import nl.jappieklooster.ISAI.collection.graph.Vertex
import nl.jappieklooster.ISAI.init.factory.path.PathFindFactory
import nl.jappieklooster.ISAI.input.InputHandler
import nl.jappieklooster.ISAI.state.global.ACommenState;
import nl.jappieklooster.ISAI.state.global.AnInputDirectingState;
import nl.jappieklooster.ISAI.world.World
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
	World world

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
				IBehaviour path = new PathFindFactory(world.environment.navGraph).planPath(selected, location)
				
				selected.add(path)

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
					clicked.invalidatedBehaviours.addAll(clicked.behaviours)
					selected = clicked
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
