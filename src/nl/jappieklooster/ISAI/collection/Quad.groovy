package nl.jappieklooster.ISAI.collection

import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector2
import nl.jappieklooster.math.vector.Vector3

class Quad {

	private Quad parent
	
	// the leafs are stored in a list
	private List<IPositionable> elements

	// when the list exceeds 4 elements the elements will be divided among the subquads
	private Quad topLeft
	private Quad topRight
	private Quad bottomLeft
	private Quad bottomRight
	
	//
	private Vector2 center
	
	Quad(){
		center = new Vector2(0)
		topLeft = null
		topRight = null
		bottomLeft = null
		bottomRight = null
		parent = null
		elements = new ArrayList<>(4)
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
