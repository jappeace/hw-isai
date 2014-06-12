package nl.jappieklooster.ISAI.world.entity

import nl.jappieklooster.ISAI.behaviour.IBehaviour;
import nl.jappieklooster.ISAI.behaviour.state.StateMachine;
import nl.jappieklooster.math.vector.Converter;
import nl.jappieklooster.math.vector.Vector3

/**
 * an actor is anything that has behaviour, like a bullet or a character
 * @author jappie
 *
 */
class Actor extends MovingEntity{
	List<IBehaviour> behaviours
	/**
	 * circumvents concurent modification errors
	 * 
	 * in the behaviours loop you can add changes to this list.
	 * and all items in this list will receive the behaviours to change them
	 */
	List<IBehaviourEditor> behaviourChanges
	Actor(){
		super()
		behaviours = new LinkedList<>()
		behaviourChanges = new LinkedList<>()
	}
	@Override
	void update(float tpf){
		behaviourChanges.each{
			it.edit(behaviours)
		}
		behaviours.each{
			if(it.chance > random.nextDouble()){
                it.execute()
			}
		}
		super.update(tpf)
	}
}

