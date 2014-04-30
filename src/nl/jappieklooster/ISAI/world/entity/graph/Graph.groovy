package nl.jappieklooster.ISAI.world.entity.graph

import com.jme3.scene.Node

class Graph {

	Node node
	List<Vertex> verteci

	Graph(){
		node = new Node("Graph node")
		verteci = new ArrayList<>(100) // reserve some space, you don't make a graph for 10 elements
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
