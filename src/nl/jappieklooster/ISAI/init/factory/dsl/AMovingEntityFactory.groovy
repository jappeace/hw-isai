package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture

import nl.jappieklooster.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.AEntityFactory
import nl.jappieklooster.ISAI.init.factory.dsl.AMaterialFactory;
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.MovingEntity
import nl.jappieklooster.ISAI.world.entity.BehavingEntity;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

abstract class AMovingEntityFactory extends AEntityFactory{

	AHasNode group
	Random random

	@Override
	void setToDefault(){
		super.setToDefault()
		movingEntity.random = random
	}
	
	void friction(float to){
		movingEntity.friction = to
	}
	
	void mass(float mass){
		movingEntity.mass = mass
	}
	
	void force(Vector3 initialForce){
		movingEntity.force = initialForce
	}
	@Override
	Entity getEntity() {
		return movingEntity;
	}
	@Override
	AHasNode getNodeContainer() {
		return group;
	}

	abstract MovingEntity getMovingEntity() 
}
