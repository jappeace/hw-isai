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
	float friction
	
	MovingEntity(){
		velocity = new Vector3()
		force = new Vector3()
		heading = new Vector3()
		mass = 1
		maxSpeed = 5
		maxForce = 10
		maxRotation = 10
		friction = 0.05
	}
	
	void setHeading(Vector3 to){
		heading = to
		geometry.rotateUpTo(Converter.toJME(to))
	}
	
	Vector3 toWorldSpace(Vector3 input){
		return Converter.fromJME(geometry.localToWorld(Converter.toJME(input), null))
	}
}

