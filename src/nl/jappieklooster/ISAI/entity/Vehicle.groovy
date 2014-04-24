package nl.jappieklooster.ISAI.entity

import nl.jappieklooster.ISAI.behaviour.ISteerable
import nl.jappieklooster.math.vector.Converter;
import nl.jappieklooster.math.vector.Vector3

class Vehicle extends MovingEntity {
	List<ISteerable> steeringBehaviours

	Vehicle(){
		super()
		steeringBehaviours = new ArrayList<>()
	}
	@Override
	void update(float tpf){
		force = new Vector3(0) // reset the forces, to put more accent on steering
		steeringBehaviours.each{
			if(it.chance > random.nextDouble()){
                it.steer()
			}
		}
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
	
	void add(ISteerable behaviour){
		behaviour.setEntity(this);
		steeringBehaviours.add(behaviour)
	}
}

