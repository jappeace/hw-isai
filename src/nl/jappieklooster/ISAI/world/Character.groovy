package nl.jappieklooster.ISAI.world

import com.jme3.scene.Spatial;

import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.behaviour.state.IStateMachineTarget
import nl.jappieklooster.ISAI.collection.graph.Graph
import nl.jappieklooster.ISAI.init.factory.path.PathFindFactory
import nl.jappieklooster.ISAI.world.entity.BehavingEntity
import nl.jappieklooster.math.vector.Vector3;

class Character implements IStateMachineTarget{

	BehavingEntity body
	private PathFindFactory pathfinder
	
	Character(Graph graph){
		pathfinder = new PathFindFactory(graph)
	}

	@Override
	Spatial getSpatial() {
		return body.spatial
	}

	@Override
	void move(Vector3 to) {
        body.behaviours.add(pathfinder.planPath(body, to))
	}
}
