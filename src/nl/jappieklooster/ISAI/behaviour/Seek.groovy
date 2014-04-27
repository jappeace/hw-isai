package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Seek extends AbstractSteerable{

	/** a closure that provides a vector3 that specifies the location where to run from*/
	Closure getToCallback
	
	@Override
	public void steer() {
		Vector3 runTo = getToCallback()
        entity.force += (runTo - entity.position).normalized * new Vector3(entity.maxForce) - entity.velocity
        return
	}

}
