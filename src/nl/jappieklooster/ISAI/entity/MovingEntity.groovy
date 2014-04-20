package nl.jappieklooster.ISAI.entity

import nl.jappieklooster.math.vector.*

abstract class MovingEntity extends Entity{
	protected Vector3 velocity
	Vector3 force
	Vector3 heading
	float mass
	float maxSpeed
	float maxForce
	float maxRotation
	
	MovingEntity(){
		velocity = new Vector3()
		force = new Vector3()
		heading = new Vector3()
		mass = 1
		maxSpeed = 10
		maxForce = 10
		maxRotation = 10
	}
	
	Vector3 localToWorld(Vector3 point){
		point *= heading
		point += position
		return point
	}
	Vector3 worldToLocal(Vector3 point){
		point -= position
		point /= heading
		return point
	}
}

