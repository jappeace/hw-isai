package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Wander implements ISteerable{

	float constraintRadius
	float circleDistance
	float jitter
	Random random
	Vector3 wanderTarget
	MovingEntity entity

	Wander(Random r){
		random = r
		wanderTarget = new Vector3()
	}
	@Override
	public void steer(float tpf) {
		wanderTarget += new Vector3(randomClamped()*jitter)
		wanderTarget = wanderTarget.normalized
		wanderTarget *= new Vector3(constraintRadius)
		entity.force += entity.toWorldSpace(wanderTarget + new Vector3(circleDistance)) - entity.position
	}
	@Override
	public void setEntity(MovingEntity to) {
		entity = to
		
	}
	private float randomClamped(){
		random.nextFloat()*2-1
	}
}
