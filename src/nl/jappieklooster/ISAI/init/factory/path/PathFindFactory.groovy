package nl.jappieklooster.ISAI.init.factory.path

import nl.jappieklooster.ISAI.collection.graph.Graph
import nl.jappieklooster.ISAI.collection.graph.Vertex
import nl.jappieklooster.ISAI.collection.oct.OctTree
import nl.jappieklooster.ISAI.init.factory.path.strategy.AStar
import nl.jappieklooster.ISAI.init.factory.path.strategy.IPathFindStrategy
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector3

class PathFindFactory {
	Graph navGraph
	float initVertexRanger = 100
	IPathFindStrategy strategy
	
	PathFindFactory(){
		strategy = new AStar()
	}

	/** uses A start to find the path*/
	Collection<Vertex> FindPath(Vector3 from, Vector3 to){
		OctTree tree = navGraph.toOctTree()
		// find starth of the graph
		Vertex start = (Vertex)tree.findClosest(from, initVertexRanger)
		// find end of the graph
		Vertex end = (Vertex)tree.findClosest(from, initVertexRanger)
		
		return strategy.findPath(start, end, navGraph)
	}
}
