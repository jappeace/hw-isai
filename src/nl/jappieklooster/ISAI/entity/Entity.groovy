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
	
	@Override
	boolean equals(Object obj){
		if(obj.is(this)){
			return true
		}
		if(! (obj instanceof Entity)){
			return false
		}
		Entity entity = (Entity) obj
		return entity.geometry == geometry && entity.position == position
	}
	
	@Override
	int hashCode(){
		(geometry.hashCode() + position.hashCode() * 5).hashCode()
	}
}
