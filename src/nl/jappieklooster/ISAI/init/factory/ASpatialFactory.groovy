package nl.jappieklooster.ISAI.init.factory

import com.jme3.scene.Spatial

import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

/** abstracts away the jme api calls, with jme abstraction */
abstract class ASpatialFactory extends AWorldFactory{
	ASpatialFactory(NeighbourTracker tracker){
		super(tracker)
	}
	abstract Spatial getSpatial()
	void location(Vector3 where){
		getSpatial().setLocalTranslation(Converter.toJME(where))	
	}
	void rotation(Vector3 how){
		getSpatial().rotateUpTo(Converter.toJME(how))
	}
	void scale(Vector3 to){
		getSpatial().setLocalScale(Converter.toJME(to))
	}

	/**
	 * set a name for the spatial for debugging or to organise init code
	 */
	void name(String name){
		getSpatial().setName(name)
	}
	
}
