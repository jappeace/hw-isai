package nl.jappieklooster.ISAI.init.factory.path.strategy

import nl.jappieklooster.ISAI.collection.graph.Graph
import nl.jappieklooster.ISAI.collection.graph.Vertex

interface IPathFindStrategy {
	Collection<Vertex> findPath(Vertex from, Vertex to, Graph graph)
}
