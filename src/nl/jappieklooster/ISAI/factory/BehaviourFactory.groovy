package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.behaviour.ISteerable
import nl.jappieklooster.ISAI.behaviour.Wander
import nl.jappieklooster.ISAI.entity.impl.Vehicle
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class BehaviourFactory{
	Vehicle vehicle
	static Random random
	BehaviourFactory(Vehicle vehicle){
		this.vehicle = vehicle
		random = random ?: new Random()
	}
	
	ISteerable wander(float constraintRadius = 3, float circleDistance = 3, float jitter = 2){
		Wander wander = new Wander(random)
		wander.constraintRadius = constraintRadius
		wander.circleDistance = circleDistance
		wander.jitter = jitter
		vehicle.add(wander) // bind to eachother
		return wander
	}
	
	
	
}
