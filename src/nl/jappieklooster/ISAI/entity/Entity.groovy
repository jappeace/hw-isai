package nl.jappieklooster.ISAI.entity

import com.jme3.scene.Geometry
import nl.jappieklooster.ISAI.IWorldItem
import nl.jappieklooster.math.vector.*

abstract class Entity implements IWorldItem{

	protected Geometry geometry
	/** world position */
	Vector3 position
	Vector3 getScale(){
		Converter.fromJME(geometry.getLocalScale())
	}
}
