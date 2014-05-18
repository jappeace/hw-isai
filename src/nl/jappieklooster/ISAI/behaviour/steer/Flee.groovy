package nl.jappieklooster.ISAI.behaviour.steer

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Flee extends AbstractBehaviour{

	/** a closure that provides a vector3 that specifies the location where to run from*/
	Closure getFromCallback
	
	@Override
	public void execute() {
		Vector3 runFrom = getFromCallback()
        entity.force += (entity.position - runFrom).normalized * new Vector3(entity.maxForce)
        return
	}

}
