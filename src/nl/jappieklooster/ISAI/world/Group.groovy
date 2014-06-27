package nl.jappieklooster.ISAI.world
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.jme3.scene.shape.Sphere;

import nl.jappieklooster.ISAI.collection.ICollectionEditor
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

/**
 * the group class has 2 purposes, the first is pure optimization, when not all items need to be updated
 * you gain time, so stuff that does need to be updated should be placed in a group
 * 
 * the second purpose is ease of order, you can for example move an entire group by moving its node,
 * it also lets the program understand different group, and help ignore them or react to them
 */
class Group extends AHasNode implements IGroupItem{
	
	List<ICollectionEditor<IGroupItem>> memberChanges
	/**
	 * stuff in the world that receves update notifactions
	 */
	List<IGroupItem> members
	
	/**
	 * some objects would like to be notified if there is an update, but they are not realy
	 * part of the group
	 */
	List<IUpdatable> listeners
	/**
	 * some worlds only contain bricks, ignore this allows them to be ignored
	 */
	boolean shouldUpdate = false

	Group(){
		super()
		// linked lists are more memory effiecient than arraylists, and since I am not planning using indexcis
		// they are the better choice for storing stuff
		members = new LinkedList<>()
		listeners = new LinkedList<>()
		memberChanges = new LinkedList<>()
	}
	
	/**
	 * copy constructor
	 */
	Group(Group source){
		super()
		members = new LinkedList<>(source.members)
		listeners = new LinkedList<>(source.listeners)
		memberChanges = new LinkedList<>(source.memberChanges)
		shouldUpdate = source.shouldUpdate
		node = source.node.clone(false)
	}

	void update(float tpf){
		if(!shouldUpdate){
			return
		}
		listeners.each{
			it.update(tpf)
		}
		memberChanges.each{
			it.edit(members)
		}
		memberChanges.clear()
		members.each{
			it.update(tpf)
		}
	}
	@Override
	boolean equals(Object obj){
		if(obj.is(this)){
			return true
		}
		if(!(obj instanceof Group)){
			return false
		}
		Group group = (Group) obj
		return group.node == node && group.members == members && group.name == name
	}
	
	@Override
	int hashCode(){
		(node.hashCode() + members.hashCode() * 3 + 48 * name.hashCode()).hashCode()
	}

}
