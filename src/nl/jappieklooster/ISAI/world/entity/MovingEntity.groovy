package nl.jappieklooster.ISAI.world.entity

import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.math.vector.*

class MovingEntity extends Entity implements IGroupItem{
	protected Vector3 velocity
	Vector3 force
	Vector3 heading
	float mass
	/**
	 * how much force will be applied
	 */
	float impulse
	float friction
	
	Random random
	MovingEntity(){
		super()
		velocity = new Vector3()
		force = new Vector3()
		heading = new Vector3()
		mass = 1
		impulse = 1
		friction = 0.10
	}
	
	void setHeading(Vector3 to){
		heading = to
		spatial.rotateUpTo(Converter.toJME(to))
	}

	@Override
	void update(float tpf){
		force -= new Vector3(friction) * velocity
		velocity += (force / new Vector3(mass))  * new Vector3(tpf) // calculate speed
		Vector3 movement = velocity * new Vector3(tpf) // calculate movement
		
		if(velocity.length != 0){
            setHeading(velocity.normalized)
		}
		spatial.move(Converter.toJME(movement)) // move the visible part

		force = new Vector3(0) // forget about the forces, velocity represent inertia
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
			entity.impulse == impulse && 
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
			impulse + 
			friction
		).hashCode() 
	}
}


