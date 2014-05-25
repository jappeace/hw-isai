package nl.jappieklooster.ISAI.init.factory.path.strategy

import java.util.Collection;

import nl.jappieklooster.ISAI.collection.graph.Graph;
import nl.jappieklooster.ISAI.collection.graph.Vertex;

class AStar implements IPathFindStrategy{

	Queue<AStarElement> considirationQueue
	Vertex target
	IAStarHeuristic heuristic
	AStar(){
		heuristic = {Vertex from, Vertex to ->
			return (from.position - to.position).length
		}	
	}
	@Override
	Collection<Vertex> findPath(Vertex from, Vertex to) {
		considirationQueue = new PriorityQueue<>()
		target = to
		
		considirationQueue.add(new AStarElement(from, 0))
		AStarElement path = AStart()
		
		Collection<Vertex> result = new Stack<>()
		
		while(path != null){
			result.add(path.vertex)
			path = path.from
		}
		
		return result;
	}
	
	AStarElement AStart(){
		while(considirationQueue.peek() != null){

			AStarElement current = considirationQueue.poll()

			if(current.vertex == target){
				return current
			}

			current.vertex.connections.each{
				considirationQueue.add(
					new AStarElement(
						current, 
						it.to, 
						it.weight + heuristic.calculateHeuristic(it.to,	target)
					)
				)
			}
		}	
		return null // nothing found
	}
	
	
	

}
