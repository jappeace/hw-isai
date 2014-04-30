package nl.jappieklooster.ISAI.init.factory.dsl.group

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture

import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.AEntityFactory
import nl.jappieklooster.ISAI.init.factory.dsl.AMaterialFactory;
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.Vehicle;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class VehicleFactory extends AEntityFactory{
	Vehicle vehicle

	Group group
	NeighbourTracker neighTracker
	Random random
	VehicleFactory(NeighbourTracker tracker){
		neighTracker = tracker

		vehicle = new Vehicle()
		vehicle.velocity = new Vector3()
		vehicle.position = new Vector3()
	}

	@Override
	void setToDefault(){
		super.setToDefault()
		vehicle.random = random
	}
	void behaviour(Closure commands){
		group.shouldUpdate = true
		BehaviourFactory factory = new BehaviourFactory(neighTracker)
		factory.random = random
		factory.vehicle = vehicle
		new DelegateClosure(to:factory).call(commands)
	}
	
	void friction(float to){
		vehicle.friction = to
	}
	
	void mass(float mass){
		vehicle.mass = mass
	}
	
	void heading(Vector3 to){
		vehicle.heading = to.normalized
	}
	@Override
	public Entity getEntity() {
		return vehicle;
	}
	@Override
	public AHasNode getNodeContainer() {
		return group;
	}
}
