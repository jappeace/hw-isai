package nl.jappieklooster.ISAI.init.factory.dsl.group

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture

import nl.jappieklooster.ISAI.behaviour.state.StateMachine
import nl.jappieklooster.ISAI.behaviour.text.TextWriter
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.AEntityFactory
import nl.jappieklooster.ISAI.init.factory.dsl.AMaterialFactory;
import nl.jappieklooster.ISAI.init.factory.dsl.AMovingEntityFactory
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.ISAI.world.entity.BehavingEntity;
import nl.jappieklooster.ISAI.world.entity.tracking.ClickablesTracker
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.ISAI.world.Character
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class BehavingEntityFactory extends AMovingEntityFactory{
	BehavingEntity behavingEntity

	NeighbourTracker neighTracker
	ClickablesTracker clickTracker
	BehavingEntityFactory(NeighbourTracker tracker){
		super()
		neighTracker = tracker

		behavingEntity = new BehavingEntity()
		behavingEntity.velocity = new Vector3()
	}
	void behaviour(Closure commands){
		group.shouldUpdate = true
		BehaviourFactory factory = new BehaviourFactory(neighTracker)
		factory.random = random
		factory.vehicle = behavingEntity
		new DelegateClosure(to:factory).call(commands)
	}

	StateMachine states(Closure commands){
		group.shouldUpdate = true
		StateMachineFactory smFactory = new StateMachineFactory()
		new DelegateClosure(to:smFactory).call(commands)
		behavingEntity.behaviours.add(smFactory.stateMachine)
		return smFactory.stateMachine
	}
	@Override
	MovingEntity getMovingEntity() {
		return behavingEntity
	}

	@Override
	TextWriter write(Closure commands){
		TextWriter writer = super.write(commands)
		// allows texts to be updated
		behavingEntity.behaviours.add(writer)
		return writer
	}
	void clickable(){
		group.shouldUpdate = true
		clickTracker.track(behavingEntity)
	}
}
