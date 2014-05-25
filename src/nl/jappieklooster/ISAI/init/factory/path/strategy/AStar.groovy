package nl.jappieklooster.ISAI.init.factory.path.strategy

import com.jme3.math.ColorRGBA
import java.util.Collection;

import nl.jappieklooster.ISAI.collection.graph.Graph;
import nl.jappieklooster.ISAI.collection.graph.Vertex;
import nl.jappieklooster.ISAI.init.factory.WireFrameFactory
class AStar implements IPathFindStrategy{

	IAStarHeuristic heuristic
	private Queue<AStarElement> considirationQueue
	private Vertex target
	private ColorRGBA debugColor
	AStar(){
		heuristic = {Vertex from, Vertex to ->
			return (from.position - to.position).length
		}	
	}
	@Override
	Collection<Vertex> findPath(Vertex from, Vertex to) {
		debugColor = WireFrameFactory.instance.createRandomColor()
		if(from == null || to == null){
			return new Stack<>()
		}
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
				it.geometry.material.setColor("Color", debugColor)
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
