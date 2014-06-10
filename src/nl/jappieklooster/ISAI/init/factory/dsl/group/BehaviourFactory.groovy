package nl.jappieklooster.ISAI.init.factory.dsl.group

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture

import nl.jappieklooster.ISAI.behaviour.steer.Explore;
import nl.jappieklooster.ISAI.behaviour.steer.Flee;
import nl.jappieklooster.ISAI.behaviour.steer.ISteeringBehaviour
import nl.jappieklooster.ISAI.behaviour.IBehaviour;
import nl.jappieklooster.ISAI.behaviour.steer.Seek;
import nl.jappieklooster.ISAI.behaviour.steer.Wander;
import nl.jappieklooster.ISAI.behaviour.steer.group.ANeighbourAware;
import nl.jappieklooster.ISAI.behaviour.steer.group.Alignment;
import nl.jappieklooster.ISAI.behaviour.steer.group.Cohesion;
import nl.jappieklooster.ISAI.behaviour.steer.group.Seperation;
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Vehicle;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class BehaviourFactory{
	Vehicle vehicle

	NeighbourTracker neighTracker
	Random random
	BehaviourFactory(NeighbourTracker neigh){
		neighTracker = neigh
		this.vehicle = vehicle
	}
	
	IBehaviour wander(float constraintRadius = 3, float circleDistance = 2, float jitter = 1){
		Wander wander = new Wander(random)
		wander.constraintRadius = constraintRadius
		wander.circleDistance = circleDistance
		wander.jitter = jitter
		bind(wander)
	}
	
	IBehaviour flee(IPositionable from){
		Flee flee = new Flee()
		flee.from = from
		bind(flee)
	}
	IBehaviour seek(IPositionable from){
		Seek seek = new Seek()
		seek.toPosition = from
		bind(seek)
	}
	IBehaviour explore(){
		bind(new Explore())
	}
	
	IBehaviour alignment(float radius = 20){
        createANeighbourAware(new Alignment(), radius)
	}
	
	IBehaviour cohesion(float radius = 40){
        createANeighbourAware(new Cohesion(), radius)
	}
	
	IBehaviour seperate(float radius = 8){
        createANeighbourAware(new Seperation(), radius)
	}
	
	/** shorthand for flocking like behavior (its a combination) */
	void flock(){
		IBehaviour seperate = seperate()
		seperate.chance = 0.3
		IBehaviour alignment = alignment()
		alignment.chance = 0.1
		IBehaviour cohesion = cohesion()
		cohesion.chance = 0.3
	}
	
	/**
	 * binds vehicle to behaviour and vice versa
	 * @param what
	 * @return
	 */
	private IBehaviour bind(ISteeringBehaviour what){
		what.entity = vehicle
		vehicle.behaviours.add(what)
		return what
	}

	private IBehaviour createANeighbourAware(ANeighbourAware what, float radius){
		what.tracker = neighTracker
		what.neighbourRadius = radius
		bind(what)
		neighTracker.addDistance(radius)
		return what
	}

}
