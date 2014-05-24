package nl.jappieklooster.ISAI.init.factory.path

import nl.jappieklooster.ISAI.collection.graph.Graph
import nl.jappieklooster.ISAI.collection.graph.Vertex
import nl.jappieklooster.ISAI.collection.oct.OctTree
import nl.jappieklooster.math.vector.Vector3

class PathFindFactory {
	Graph navGraph
	float initVertexRanger = 100

	/** uses A start to find the path*/
	Collection<Vertex> FindPath(Vector3 from, Vector3 to){
		OctTree tree = navGraph.toOctTree()
		tree.find(from, initVertexRanger)
	}
}
