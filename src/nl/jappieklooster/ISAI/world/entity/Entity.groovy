package nl.jappieklooster.ISAI.world.entity

import com.jme3.material.Material
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.math.vector.*
import com.jme3.scene.Node

abstract class Entity implements IPositionable{

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
	
	Spatial getSpatial(){
		return localSpace
	}
	
	protected Node getLocalSpace(){
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
}
