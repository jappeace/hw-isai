package nl.jappieklooster.ISAI.world

import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

import com.jme3.scene.Node

/**
 * this class  represents somthing that has a node
 * usaly this mean it is a container class that holds multiple objects that have a 3d represitatation
 * @author jappie
 *
 */
abstract class AHasNode implements IPositionable{

	/**
	 * the view of this world, this is how jme3 decides what to render and how
	 * each node has its own cooridinate system, that can rotate and be moved
	 */
	Node node
	
	AHasNode(){
		node = new Node("rootnode of  " + System.identityHashCode() + " creation time: " + System.nanoTime())
	}

	/** local position*/
	public Vector3 getPosition() {
		return Converter.fromJME(node.localTranslation)
	}
	
	void setName(String to){
		node.name = to
	}
	
	String getName(){
		node.name
	}
}
