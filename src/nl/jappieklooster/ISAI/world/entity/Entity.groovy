package nl.jappieklooster.ISAI.world.entity

import com.jme3.material.Material
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.math.vector.*

import com.jme3.scene.Node

import nl.jappieklooster.ISAI.world.IHasNode

class Entity implements IPositionable, IHasNode{

	Geometry geometry
	private Node localSpace
	
	Entity(){
		localSpace = new Node("Local entity space")
	}
	
	Vector3 getPosition(){
		return Converter.fromJME(localSpace.getWorldTranslation())
	}

	void setPosition(Vector3 to){
		localSpace.setLocalTranslation(Converter.toJME(to))
	}
	
	@Override
	Spatial getSpatial(){
		// returning the node instead of geometry is intentional
		// so all tranformations would include the stuff thats attached to the node
		// this makes for example attaching a text to somthing easy and mutatable
		return localSpace
	}
	
	Node getNode(){
		return localSpace
	}
	
	Material getMaterial(){
		return geometry.material
	}
	
	void setMesh(Mesh to){
		geometry.mesh = to
	}
	
	/**
	 * assign a new geometry to this entety, clearing the local space
	 * @param to
	 */
	void setGeometry(Geometry to){
		// if there was a geometry, delete it from localspace
		if(geometry){
			// might be tempted to dettach all children.. but that is unexcpected for a setter
			// this is probably a pleasant suprise
            localSpace.detachChild(geometry)
		}
		localSpace.attachChild(to)
		geometry = to
	}

	/**
	 * add somthing to the entitie's local space
	 * @param child
	 */
	void attach(Spatial child){
		localSpace.attachChild(child)
	}

	void detach(Spatial child){
		localSpace.detachChild(child)
	}
	Vector3 getScale(){
		Converter.fromJME(localSpace.getLocalScale())
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
		return entity.position == position
	}
	
	@Override
	int hashCode(){
		return (position.hashCode() * 5).hashCode()
	}
	
	Vector3 toWorldSpace(Vector3 input){
		
		// fixes the fact that a spatial can be moved by chancing a parent node, however my position tracking does
		// not know of this, so force calculation would result in huge numbers
		return Converter.fromJME(spatial.localToWorld(Converter.toJME(input), null))
	}

	@Override
	void move(Vector3 to) {
		spatial.move(Converter.toJME(to))
	}
}
