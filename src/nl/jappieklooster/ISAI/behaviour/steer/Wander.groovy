package nl.jappieklooster.ISAI.behaviour.steer

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Wander extends AbstractBehaviour{

	float constraintRadius
	float circleDistance
	float jitter
	Random random
	Vector3 wanderTarget

	Wander(Random r){
		random = r
		wanderTarget = new Vector3()
	}
	@Override
	public void execute() {
		wanderTarget += new Vector3(randomClamped()*jitter, randomClamped()*jitter, randomClamped()*jitter).normalized
		wanderTarget = wanderTarget.normalized
		wanderTarget *= new Vector3(constraintRadius)
		entity.force += entity.toWorldSpace(wanderTarget + new Vector3(circleDistance)) - entity.position
	}
	private float randomClamped(){
		random.nextFloat()*2-1
	}
}
