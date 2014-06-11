package nl.jappieklooster.ISAI.world.entity

import nl.jappieklooster.ISAI.behaviour.IBehaviour;
import nl.jappieklooster.ISAI.behaviour.state.StateMachine;
import nl.jappieklooster.math.vector.Converter;
import nl.jappieklooster.math.vector.Vector3

class Actor extends MovingEntity{
	List<IBehaviour> behaviours
	/**
	 * circumvents concurent modification errors
	 */
	List<IBehaviour> invalidatedBehaviours
	Actor(){
		super()
		behaviours = new LinkedList<>()
		invalidatedBehaviours = new LinkedList<>()
	}
	@Override
	void update(float tpf){
		force = new Vector3(0) // reset the forces, to put more accent on steering
		behaviours.each{
			if(it.chance > random.nextDouble()){
                it.execute()
			}
		}
		behaviours.removeAll(invalidatedBehaviours)
		invalidatedBehaviours = new LinkedList<>()

		super.update(tpf)
	}
}

