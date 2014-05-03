package nl.jappieklooster.ISAI.collection

import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector2
import nl.jappieklooster.math.vector.Vector3

class OctTree {

	private Vector3 center
	private Vector3 halfDimension
	
	private OctTree children
	
	OctTree(Vector3 center, Vector3 halfDimension){
		this.center = center
		this.halfDimension = halfDimension
		children = null
	}
	
	void add(IPositionable element){
	}
	
	IPositionable get(Vector3 location){
		
	}
	
	Collection<IPositionable> find(Vector3 location, float radius){
		
	}

}
