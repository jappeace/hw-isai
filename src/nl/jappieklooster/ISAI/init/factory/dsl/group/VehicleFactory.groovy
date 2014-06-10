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
import nl.jappieklooster.ISAI.init.factory.dsl.AMovingEntityFactory
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.ISAI.world.entity.Vehicle;
import nl.jappieklooster.ISAI.world.entity.tracking.ClickablesTracker
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class VehicleFactory extends AMovingEntityFactory{
	Vehicle vehicle

	NeighbourTracker neighTracker
	ClickablesTracker clickTracker
	VehicleFactory(NeighbourTracker tracker){
		super()
		neighTracker = tracker

		vehicle = new Vehicle()
		vehicle.velocity = new Vector3()
		vehicle.position = new Vector3()
	}
	void behaviour(Closure commands){
		group.shouldUpdate = true
		BehaviourFactory factory = new BehaviourFactory(neighTracker)
		factory.random = random
		factory.vehicle = vehicle
		new DelegateClosure(to:factory).call(commands)
	}

	void states(Closure commands){
		group.shouldUpdate = true
		StateMachineFactory smFactory = new StateMachineFactory()
		new DelegateClosure(to:smFactory).call(commands)
		smFactory.stateMachine.vehicle = vehicle
		vehicle.behaviours.add(smFactory.stateMachine)
	}
	@Override
	MovingEntity getMovingEntity() {
		return vehicle
	}
	void clickable(){
		group.shouldUpdate = true
		clickTracker.track(vehicle)
	}
}
