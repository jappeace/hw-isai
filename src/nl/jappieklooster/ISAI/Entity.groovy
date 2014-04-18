package nl.jappieklooster.ISAI

import com.jme3.scene.Geometry
import nl.jappieklooster.math.vector.Vector3

abstract class Entity {

	abstract update(float tpf);
	private Geometry geom;
	Vector3 position(){
		
	}
}
