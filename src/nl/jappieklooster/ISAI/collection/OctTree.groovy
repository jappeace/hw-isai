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
		if(elements){
			elements.add(element)
			if(elements.size() > 3){
				divide()
				return
			}
			return
		}
		addToSubQuad(element)
	}
	
	IPositionable get(Vector2 location){
		
	}
	
	IPositionable find(Vector2 location, float radius){
		
	}
	private void divide(){

		elements = null // destroy own refrences to elements
	}
	
	private void addToSubQuad(IPositionable element){
		
	}
	int size(){
		if(elements){
			return elements.size()
		}
		return topLeft.size() + topRight.size() + bottomLeft.size() + bottomRight.size()
	}
}
