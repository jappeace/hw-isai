package nl.jappieklooster.ISAI.entity

import nl.jappieklooster.math.vector.*

abstract class MovingEntity extends Entity{
	Vector3 velocity
	Vector3 heading
	float mass
	float maxSpeed
	float maxForce
	float maxRotation
}
