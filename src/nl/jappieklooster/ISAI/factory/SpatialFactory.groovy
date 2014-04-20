package nl.jappieklooster.ISAI.factory

import com.jme3.scene.Spatial
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

/** abstracts away the jme api calls, with jme abstraction */
abstract class SpatialFactory{

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

	
}
