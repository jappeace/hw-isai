package nl.jappieklooster.ISAI.init.factory.path.strategy

import com.jme3.math.ColorRGBA
import java.util.Collection;

import nl.jappieklooster.ISAI.collection.graph.Edge
import nl.jappieklooster.ISAI.collection.graph.Graph;
import nl.jappieklooster.ISAI.collection.graph.Vertex;
import nl.jappieklooster.ISAI.init.factory.WireFrameFactory
class AStar implements IPathFindStrategy{

	IAStarHeuristic heuristic
	private Queue<AStarElement> considirationQueue
	private Vertex target
	private ColorRGBA debugColor
	/**
	 * allows resetting of the edge state (after the astar is done)
	 */
	private Collection<Edge> visited
	AStar(){
		heuristic = {Vertex from, Vertex to ->
			return (from.position - to.position).length
		}	
	}
	@Override
	Collection<Vertex> findPath(Vertex from, Vertex to) {
		if(from == null || to == null){
			return new LinkedList<>()
		}
		reset()

		target = from
		considirationQueue.add(new AStarElement(to, 0))

		AStarElement starResult = AStart()
		
		// reset the edges so next time the algoritm doesn't think its done imidiatly
		visited.each{
			it.visited = false
		}

		Collection<Vertex> result = new LinkedList<>()

		// to inverse the star result and add it to a collection put evreything on a stack
		while(starResult != null){
			result.add(starResult.vertex)
			starResult = starResult.from
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
				if(it.visited){
					return
				}
				it.visited = true
				visited.add(it)
				
				it.geometry.material.setColor("Color", debugColor)
				considirationQueue.add(
					new AStarElement(
						current, 
						it.to, 
						(float)(it.weight + heuristic.calculateHeuristic(it.to,	target))
					)
				)
			}
		}	
		return null // nothing found
	}
	
	
	private void reset(){
		debugColor = WireFrameFactory.instance.createRandomColor()

		considirationQueue = new PriorityQueue<>()
		visited = new LinkedList<>()
	}
	

}
