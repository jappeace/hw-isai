package nl.jappieklooster.ISAI.behaviour.steer

import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Flee extends AbstractSteeringBehaviour{

	/** a closure that provides a vector3 that specifies the location where to run from*/
	IPositionable from
	
	@Override
	public void execute() {
        entity.force += (entity.position - from.position).normalized * new Vector3(entity.maxForce)
        return
	}

}
