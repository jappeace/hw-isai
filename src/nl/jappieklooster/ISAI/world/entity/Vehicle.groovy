package nl.jappieklooster.ISAI.world.entity

import nl.jappieklooster.ISAI.behaviour.IBehaviour;
import nl.jappieklooster.math.vector.Converter;
import nl.jappieklooster.math.vector.Vector3

class Vehicle extends MovingEntity {
	List<IBehaviour> steeringBehaviours
	/**
	 * circumvents concurent modification errors
	 */
	List<IBehaviour> invalidatedBehaviours

	Vehicle(){
		super()
		steeringBehaviours = new LinkedList<>()
		invalidatedBehaviours = new LinkedList<>()
	}
	@Override
	void update(float tpf){
		force = new Vector3(0) // reset the forces, to put more accent on steering
		steeringBehaviours.each{
			if(it.chance > random.nextDouble()){
                it.execute()
			}
		}
		steeringBehaviours.removeAll(invalidatedBehaviours)
		invalidatedBehaviours = new LinkedList<>()
		velocity += (force / new Vector3(mass))  * new Vector3(tpf) // calculate speed
		velocity -= new Vector3(friction) * velocity
		Vector3 movement = velocity * new Vector3(tpf) // calculate movement
		
		// keep track of the world location
		position += movement
		
		if(velocity.length != 0){
            heading = velocity.normalized
		}
		
		geometry.move(Converter.toJME(movement))
	}
	
	void add(IBehaviour behaviour){
		behaviour.setEntity(this);
		steeringBehaviours.add(behaviour)
	}
}

