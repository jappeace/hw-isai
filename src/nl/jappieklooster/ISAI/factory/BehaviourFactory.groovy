package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.behaviour.Flee
import nl.jappieklooster.ISAI.behaviour.ISteerable
import nl.jappieklooster.ISAI.behaviour.Wander
import nl.jappieklooster.ISAI.behaviour.group.Alignment
import nl.jappieklooster.ISAI.behaviour.group.Cohesion
import nl.jappieklooster.ISAI.behaviour.group.Seperation
import nl.jappieklooster.ISAI.entity.impl.Vehicle
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class BehaviourFactory{
	World world
	Vehicle vehicle
	private static Random random
	BehaviourFactory(Vehicle vehicle, World world){
		this.vehicle = vehicle
		this.world = world
		random = random ?: new Random()
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
	
	ISteerable alignment(float radius = 160){
		Alignment alignment = new Alignment()
		alignment.world = world
		alignment.neighbourRadius = radius
		bind(alignment)
	}
	
	ISteerable cohesion(float radius = 100){
		Cohesion cohesion = new Cohesion()
		cohesion.world = world
		cohesion.neighbourRadius = radius
		bind(cohesion)
	}
	
	ISteerable seperate(float radius = 50){
		Seperation seperation = new Seperation()
		seperation.world = world
		seperation.neighbourRadius = radius
		bind(seperation)
	}
	
	/** shorthand for flocking like behavior (its a combination) */
	void flock(){
		ISteerable seperate = seperate()
		seperate.power = new Vector3(1)
		ISteerable cohesion = cohesion()
		cohesion.power = new Vector3(0.5)
		ISteerable alignment = alignment()
		alignment.power = new Vector3(0.7)
		ISteerable wander = wander()
		wander.power = new Vector3(1)
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

	
	
	
}
