package nl.jappieklooster.ISAI.behaviour.steer

import nl.jappieklooster.ISAI.behaviour.ICompletableBehaviour;
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Seek extends AbstractSteeringBehaviour implements ICompletableBehaviour{

	/** somting to go to, can be a closure as long as it returns a vector3*/
	IPositionable toPosition
	/**
	 * when am I close enough to consider myself to be arrived
	 */
	float ariveDistance = 3

	
	@Override
	void execute() {
		Vector3 runTo = toPosition.position
        entity.force += (runTo - entity.position).normalized * new Vector3(entity.impulse) - entity.velocity
        return
	}

	@Override
	public boolean isDone() {
		return (ariveDistance * ariveDistance > (toPosition.position - entity.position).lengthSq)
	}

}

