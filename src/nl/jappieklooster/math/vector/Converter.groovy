package nl.jappieklooster.math.vector

import com.jme3.math.Vector2f
import com.jme3.math.Vector3f

/** convert between jme and mine, mine are operator overloaded...*/
class Converter {

	static Vector2f toJME(Vector2 from){
		new Vector2f(from.x, from.y)
	}
	static Vector2 fromJME(Vector2f from){
		new Vector2(from.x, from.y)
	}
	
	static Vector3f toJME(Vector3 from){
		new Vector3f(from.x, from.y, from.z)
	}
	static Vector3 fromJME(Vector3f from){
		new Vector3(from.x, from.y, from.z)
	}
}
