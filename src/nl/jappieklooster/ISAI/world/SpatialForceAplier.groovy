package nl.jappieklooster.ISAI.world

import com.jme3.scene.Spatial;

import nl.jappieklooster.math.vector.*

/**
 * a class that calculates the movement depending on the force present.
 * then it applies this force to the spatial, this is commen code in movingentity and group
 * @author jappie
 *
 */
class SpatialForceAplier implements IUpdatable, IHasSpatial{

	IHasSpatial targetSpatialContainer

	Vector3 force
	Vector3 heading
	Vector3 velocity
	float mass
	float friction

	SpatialForceAplier(IHasSpatial target){
		velocity = new Vector3()
		force = new Vector3()
		heading = new Vector3()
		mass = 1
		friction = 0.10
		targetSpatialContainer = target
	}
	void setHeading(Vector3 to){
		heading = to
		spatial.rotateUpTo(Converter.toJME(to))
	}

	@Override
	void update(float tpf){
		force -= new Vector3(friction) * velocity
		velocity += (force / new Vector3(mass))  * new Vector3(tpf) // calculate speed
		Vector3 movement = velocity * new Vector3(tpf) // calculate movement
		
		if(velocity.length != 0){
            setHeading(velocity.normalized)
		}
		spatial.move(Converter.toJME(movement)) // move the visible part

		force = new Vector3(0) // forget about the forces, velocity represent inertia
	}

	@Override
	public Vector3 getPosition() {
		return targetSpatialContainer.position
	}

	@Override
	public Spatial getSpatial() {
		return targetSpatialContainer.spatial
	}
}
