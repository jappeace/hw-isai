package nl.jappieklooster.ISAI
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.jme3.scene.shape.Sphere;

import nl.jappieklooster.ISAI.entity.Entity
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter
class World implements IWorldItem{
	Node node
	List<IWorldItem> entities
	List<IUpdatable> listeners
	void update(float tpf){
		listeners.each{
			it.update(tpf)
		}
		entities.each{
			it.update(tpf)
		}
	}
	/** local position*/
	public Vector3 getPosition() {
		return Converter.fromJME(node.localTranslation)
	}
	
	@Override
	boolean equals(Object obj){
		if(obj.is(this)){
			return true
		}
		if(!(obj instanceof World)){
			return false
		}
		World world = (World) obj
		return world.node == node && world.entities == entities
	}
	
	@Override
	int hashCode(){
		(node.hashCode() + entities.hashCode() * 3).hashCode()
	}

}
