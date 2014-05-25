package nl.jappieklooster.ISAI.init.factory.path.strategy

import nl.jappieklooster.ISAI.collection.graph.Graph
import nl.jappieklooster.ISAI.collection.graph.Vertex

interface IAStarHeuristic {
	float calculateHeuristic(Vertex from, Vertex to)
}
