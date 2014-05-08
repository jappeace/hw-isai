package nl.jappieklooster.ISAI.collection.graph

import com.jme3.scene.Node
import nl.jappieklooster.ISAI.world.AHasNode
import nl.jappieklooster.ISAI.collection.oct.OctTree
import nl.jappieklooster.math.vector.Vector3

class Graph extends AHasNode{

	Collection<Vertex> verteci

	Graph(){
		super()
		verteci = new LinkedList<>() // reserve some space, you don't make a graph for 10 elements
	}
	
	/**
	 * utitlity function that attaches the vertex to the list and also adds the debugshape of the vertex to this graph
	 * @param what
	 */
	void add(Vertex what){
		verteci.add(what)
		node.attachChild(what.node)
	}
	
	void connect(Vertex from, Vertex to){
		if(!verteci.contains(from)){
			add(from)
		}
		if(!verteci.contains(to)){
			add(to)
		}
		from.connect(to)
	}
	
}
