package nl.jappieklooster.ISAI.entity

import com.jme3.scene.Geometry
import nl.jappieklooster.math.vector.*

abstract class Entity {

	abstract void  update(float tpf);

	protected Geometry geometry
	Vector3 position
	Vector3 getScale(){
		Converter.fromJME(geometry.getLocalScale())
	}
}
