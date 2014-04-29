package nl.jappieklooster.ISAI.world.entity.graph

import com.jme3.scene.Node

class Graph {

	Node node
	List<Vertex> verteci

	Graph(){
		node = new Node("Graph node")
		verteci = new ArrayList<>(100) // reserve some space, you don't make a graph for 10 elements
	}
}
