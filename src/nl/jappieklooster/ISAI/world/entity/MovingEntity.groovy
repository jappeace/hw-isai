package nl.jappieklooster.ISAI.world.entity

import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.math.vector.*

abstract class MovingEntity extends Entity implements IGroupItem{
	protected Vector3 velocity
	Vector3 force
	Vector3 heading
	float mass
	float maxForce
	float maxRotation
	float friction
	
	Random random
	MovingEntity(){
		super()
		velocity = new Vector3()
		force = new Vector3()
		heading = new Vector3()
		mass = 1
		maxForce = 10
		maxRotation = 10
		friction = 0.01
	}
	
	void setHeading(Vector3 to){
		heading = to
		spatial.rotateUpTo(Converter.toJME(to))
	}
	
	@Override
	boolean equals(Object obj){
		if(obj.is(this)){
			return true
		}
		if(! (obj instanceof MovingEntity)){
			return false
		}
		MovingEntity entity = (MovingEntity) obj
		
		// first compare primitives because they are lightweight
		if(!(
			entity.mass == mass && 
			entity.maxForce == maxForce && 
			entity.maxRotation == maxRotation && 
			entity.friction == friction
        )){
			return false
		}

		// now compare vectors (less lightwight)
		if(!(
			entity.velocity == velocity &&
			entity.force == force &&
			entity.heading == heading
        )){
            return false
		}
		if(!super.equals(entity)){
			return false
		}
		return true
	}
	
	@Override
	int hashCode(){
        return (
			super.hashCode() * 3 + 
			velocity.hashCode() * 5 + 
			force.hashCode() * 7 + 
			heading.hashCode() * 9 +
            mass * 5+ 
			maxForce + 
			maxRotation + 
			friction
		).hashCode() 
	}
}


