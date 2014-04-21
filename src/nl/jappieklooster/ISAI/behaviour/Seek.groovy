package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Seek extends AbstractSteerable{

	/** a closure that provides a vector3 that specifies the location where to run from*/
	Closure getFromCallback
	
	@Override
	public void steer() {
		Vector3 runTo = getFromCallback()
        entity.force += (((runTo - entity.position).normalized * new Vector3(entity.maxForce)) - entity.velocity) * power
        return
	}

}
