package nl.jappieklooster.ISAI.world

import com.jme3.scene.Spatial;

import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.behaviour.ICompletable
import nl.jappieklooster.ISAI.collection.AddToCollection
import nl.jappieklooster.ISAI.collection.graph.Graph
import nl.jappieklooster.ISAI.init.factory.path.PathFindFactory
import nl.jappieklooster.ISAI.world.entity.BehavingEntity
import nl.jappieklooster.ISAI.world.mortal.IMortal
import nl.jappieklooster.ISAI.world.mortal.IWeapon
import nl.jappieklooster.math.vector.Vector3;

/**
 * a character can execute tasks like pathfinding.
 * @author jappie
 *
 */
class Character implements ICompletable, IMortal, IGroupItem{

	BehavingEntity body
	private PathFindFactory pathfinder
	private ICompletable currentTask
	float health
	
	IWeapon shotgun
	IWeapon pistol
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
		body.behaviourChanges.add(new AddToCollection<IBehaviour>(currentTask))
	}

	@Override
	boolean isDone() {
		if(currentTask == null)	{
			return true
		}
		return currentTask.isDone()
	}

	@Override
	Vector3 getPosition() {
		return body.position
	}

	void setHealth(float to){
		health = to
		println "new health = " + to
	}

	@Override
	public void update(float tpf) {
		body.update(tpf)
		pistol.update(tpf)
		shotgun.update(tpf)
	}
}
