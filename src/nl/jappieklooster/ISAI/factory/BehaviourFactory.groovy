package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.behaviour.Explore
import nl.jappieklooster.ISAI.behaviour.Flee
import nl.jappieklooster.ISAI.behaviour.ISteerable
import nl.jappieklooster.ISAI.behaviour.Seek
import nl.jappieklooster.ISAI.behaviour.Wander
import nl.jappieklooster.ISAI.behaviour.group.ANeighbourAware
import nl.jappieklooster.ISAI.behaviour.group.Alignment
import nl.jappieklooster.ISAI.behaviour.group.Cohesion
import nl.jappieklooster.ISAI.behaviour.group.Seperation
import nl.jappieklooster.ISAI.entity.impl.Vehicle
import nl.jappieklooster.ISAI.entity.tracking.NeighbourTracker
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class BehaviourFactory extends AWorldFactory{
	Vehicle vehicle
	BehaviourFactory(Vehicle vehicle, World world, NeighbourTracker neigh, Random random){
		super(world, neigh, random)
		this.vehicle = vehicle
	}
	
	ISteerable wander(float constraintRadius = 3, float circleDistance = 2, float jitter = 1){
		Wander wander = new Wander(random)
		wander.constraintRadius = constraintRadius
		wander.circleDistance = circleDistance
		wander.jitter = jitter
		bind(wander)
	}
	
	ISteerable flee(Closure from){
		Flee flee = new Flee()
		flee.getFromCallback = from
		bind(flee)
	}
	ISteerable seek(Closure from){
		Seek flee = new Seek()
		flee.getToCallback = from
		bind(flee)
	}
	ISteerable explore(){
		bind(new Explore())
	}
	
	ISteerable alignment(float radius = 20){
        createANeighbourAware(new Alignment(), radius)
	}
	
	ISteerable cohesion(float radius = 30){
        createANeighbourAware(new Cohesion(), radius)
	}
	
	ISteerable seperate(float radius = 12){
        createANeighbourAware(new Seperation(), radius)
	}
	
	/** shorthand for flocking like behavior (its a combination) */
	void flock(){
		ISteerable seperate = seperate()
		seperate.chance = 0.5
		ISteerable alignment = alignment()
		alignment.chance = 0.3
		ISteerable cohesion = cohesion()
		cohesion.chance = 0.2
		ISteerable wander = wander()
		wander.chance = 0.01
	}
	
	/**
	 * binds vehicle to behaviour and vice versa
	 * @param what
	 * @return
	 */
	private ISteerable bind(ISteerable what){
		vehicle.add(what)
		return what
	}

	private ISteerable createANeighbourAware(ANeighbourAware what, float radius){
		what.tracker = neighTracker
		what.neighbourRadius = radius
		bind(what)
		neighTracker.addDistance(radius)
		return what
	}

}
