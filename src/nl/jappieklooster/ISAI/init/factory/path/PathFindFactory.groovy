package nl.jappieklooster.ISAI.init.factory.path

import nl.jappieklooster.ISAI.behaviour.BehaviourScheduler
import nl.jappieklooster.ISAI.behaviour.change.ChangeBehaviour
import nl.jappieklooster.ISAI.behaviour.change.RemoveBehaviour
import nl.jappieklooster.ISAI.behaviour.steer.Seek
import nl.jappieklooster.ISAI.collection.graph.Graph
import nl.jappieklooster.ISAI.collection.graph.Vertex
import nl.jappieklooster.ISAI.collection.oct.OctTree
import nl.jappieklooster.ISAI.init.factory.path.strategy.AStar
import nl.jappieklooster.ISAI.init.factory.path.strategy.IPathFindStrategy
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.entity.BehavingEntity
import nl.jappieklooster.math.vector.Vector3

class PathFindFactory {
	Graph navGraph
	float initVertexRanger = 100
	IPathFindStrategy strategy
	
	PathFindFactory(Graph graph){
		navGraph = graph
		strategy = new AStar()
	}

	/** uses A start to find the path*/
	Collection<Vertex> findPath(Vector3 from, Vector3 to){
		OctTree tree = navGraph.toOctTree()
		// find starth of the graph
		Vertex start = (Vertex)tree.findClosest(from, initVertexRanger)
		// find end of the graph
		Vertex end = (Vertex)tree.findClosest(to, initVertexRanger)
		
		return strategy.findPath(start, end)
	}
	BehaviourScheduler planPath(BehavingEntity forVehicle, Vector3 to){
		Collection<Vertex> pathpoints = findPath(forVehicle.position, to)
		BehaviourScheduler result = new BehaviourScheduler()
		pathpoints.each{ Vertex vert ->
			result.add(new Seek(entity: forVehicle, toPosition:{vert.position}))
		}
		
		result.add(new Seek(entity: forVehicle, toPosition:{to}))
		
		result.add(new ChangeBehaviour(forVehicle, new RemoveBehaviour(result)))
		
		return result
	}
}
