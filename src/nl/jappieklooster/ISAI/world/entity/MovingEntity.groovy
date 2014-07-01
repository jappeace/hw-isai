package nl.jappieklooster.ISAI.world.entity

import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.ISAI.world.SpatialForceAplier
import nl.jappieklooster.math.vector.*

class MovingEntity extends Entity implements IGroupItem{
	
	private SpatialForceAplier forces
	/**
	 * how much force will be applied
	 */
	float impulse
	
	Random random
	MovingEntity(){
		super()
		impulse = 1
		
		forces = new SpatialForceAplier(this)
	}
	
	void setFriction(float to){
		forces.friction = to
	}
	
	void setMass(float mass){
		forces.mass = mass
	}
	
	void setForce(Vector3 initialForce){
		forces.force = initialForce
	}
	Vector3 getForce(){
		forces.force
	}
	@Override
	void update(float tpf){
		forces.update(tpf)
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
		
		if(!(
			entity.forces == forces
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
			forces.hashCode() +
			impulse 
		).hashCode() 
	}
}


