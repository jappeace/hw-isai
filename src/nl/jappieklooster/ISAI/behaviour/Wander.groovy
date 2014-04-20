package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Wander extends AbstractSteerable{

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
	public void steer() {
		wanderTarget += new Vector3(randomClamped()*jitter, randomClamped()*jitter, randomClamped()*jitter).normalized
		wanderTarget = wanderTarget.normalized
		wanderTarget *= new Vector3(constraintRadius)
		entity.force += (entity.toWorldSpace(wanderTarget + new Vector3(circleDistance)) - entity.position)*power
	}
	private float randomClamped(){
		random.nextFloat()*2-1
	}
}
