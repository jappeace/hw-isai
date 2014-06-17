package nl.jappieklooster.ISAI.world

import com.jme3.scene.Spatial;

import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.behaviour.ICompletable
import nl.jappieklooster.ISAI.behaviour.change.AddBehaviour
import nl.jappieklooster.ISAI.collection.graph.Graph
import nl.jappieklooster.ISAI.init.factory.path.PathFindFactory
import nl.jappieklooster.ISAI.world.entity.BehavingEntity
import nl.jappieklooster.math.vector.Vector3;

/**
 * a character can execute tasks like pathfinding.
 * @author jappie
 *
 */
class Character implements ICompletable{

	BehavingEntity body
	private PathFindFactory pathfinder
	private ICompletable currentTask
	
	Character(Graph graph){
		currentTask = null
		body = null
		pathfinder = new PathFindFactory(graph)
	}

	Spatial getSpatial() {
		return body.spatial
	}

	void move(Vector3 to) {
		currentTask = pathfinder.planPath(body, to)
		body.behaviourChanges.add(new AddBehaviour(currentTask))

	}

	@Override
	boolean isDone() {
		if(currentTask == null)	{
			return true
		}
		return currentTask.isDone()
	}
}