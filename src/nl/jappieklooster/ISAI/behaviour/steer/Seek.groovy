package nl.jappieklooster.ISAI.behaviour.steer

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.behaviour.ICompletable;
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Seek extends AbstractBehaviour implements ICompletable{

	/** a closure that provides a vector3 that specifies the location where to run from*/
	Closure getToCallback
	/**
	 * when am I close enough to consider myself to be arrived
	 */
	float ariveDistance = 3

	
	@Override
	void execute() {
		Vector3 runTo = getToCallback()
        entity.force += (runTo - entity.position).normalized * new Vector3(entity.maxForce) - entity.velocity
        return
	}

	@Override
	public boolean isDone() {
		return (ariveDistance * ariveDistance > (getToCallback() - entity.position).lengthSq)
	}

}

