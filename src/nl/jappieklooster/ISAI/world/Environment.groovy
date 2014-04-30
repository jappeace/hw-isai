package nl.jappieklooster.ISAI.world
import com.jme3.scene.Node
import com.jme3.scene.Spatial

import nl.jappieklooster.ISAI.world.entity.graph.Graph
import nl.jappieklooster.math.vector.Converter
import nl.jappieklooster.math.vector.Vector3
class Environment implements IPositionable{
	
	/**
	 * The stuff insede the envoirement is stored in a jme3 scene node
	 */
	Node node

	/**
	 * the graph is stored in my own graph implementation
	 */
	Graph navigationGraph
	
	Environment(){
		node = new Node()
	}

	@Override
	public Vector3 getPosition() {
		return Converter.fromJME(node.getWorldTranslation())
	}
}
