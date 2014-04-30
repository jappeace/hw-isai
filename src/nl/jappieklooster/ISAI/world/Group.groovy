package nl.jappieklooster.ISAI.world
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.jme3.scene.shape.Sphere;

import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter
class Group implements IGroupItem{
	
	/**
	 * the view of this world, this is how jme3 decides what to render
	 */
	Node node
	
	/**
	 * stuff in the world that receves update notifactions
	 */
	List<IGroupItem> entities
	
	/**
	 * some objects would like to be notified if there is an update, but they are not realy
	 * part of the world
	 */
	List<IUpdatable> listeners
	/**
	 * some worlds only contain bricks, ignore this allows them to be ignored
	 */
	boolean shouldUpdate = false
	/**
	 * used for identifying levels, the bulk of worlds would not use this
	 */
	String name = ""

	Group(){
		node = new Node("rootnode of  " + System.identityHashCode() + " creation time: " + System.nanoTime())
		
		// linked lists are more memory effiecient than arraylists, and since I am not planning using indexcis
		// they are the better choice for storing stuff
		entities = new LinkedList<>()
		listeners = new LinkedList<>()
	}
	void update(float tpf){
		if(!shouldUpdate){
			return
		}
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
		if(!(obj instanceof Group)){
			return false
		}
		Group world = (Group) obj
		return world.node == node && world.entities == entities && world.name == name
	}
	
	@Override
	int hashCode(){
		(node.hashCode() + entities.hashCode() * 3 + 48 * name.hashCode()).hashCode()
	}

}
