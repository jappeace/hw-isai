package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.scene.Spatial

import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

/** abstracts away the jme api calls, with jme abstraction */
abstract class ASpatialFactory{
	ASpatialFactory(){
		super()
	}
	protected abstract Spatial getSpatial()
	void location(Vector3 where){
		getSpatial().setLocalTranslation(Converter.toJME(where))	
	}
	void rotation(Vector3 how){
		getSpatial().rotateUpTo(Converter.toJME(how))
	}
	void scale(Vector3 to){
		getSpatial().setLocalScale(Converter.toJME(to))
	}
	void scale(float evenScale){
		getSpatial().setLocalScale(evenScale)
	}
	
	void scale(float x, float y, float z){
		getSpatial().setLocalScale(x, y, z)
	}
	
	void location(float x, float y, float z){
		getSpatial().setLocalTranslation(x, y, z)
	}
	
	void rotation(float x, float y, float z){
		getSpatial().rotate(x, y, z)
	}

	/**
	 * set a name for the spatial for debugging or to organise init code
	 */
	void name(String name){
		getSpatial().setName(name)
	}
	
}
