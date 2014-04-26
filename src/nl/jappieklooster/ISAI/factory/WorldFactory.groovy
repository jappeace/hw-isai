package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.entity.Vehicle;
import nl.jappieklooster.ISAI.entity.tracking.NeighbourTracker
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

import com.jme3.scene.Node
import com.jme3.scene.Spatial;

class WorldFactory extends SpatialFactory{

	AssetManager assetManager
	WorldFactory(AssetManager manager, Random random){
		super(new World(), new NeighbourTracker(), random)

		assetManager = manager

		world.node = new Node("rootnode of world: " + System.identityHashCode(world) + " creation time: " + System.nanoTime())
		world.entities = new LinkedList<>()
		world.listeners = new LinkedList<>()
		
		world.listeners.add(neighTracker)

		neighTracker.world = world
	}
	
	World make(Closure commands){
		new DelegateClosure(to: this).call(commands)
		return world
	}
	
	/** create a new vehicle */
	Vehicle vehicle(Closure commands){
		VehicleFactory factory = new VehicleFactory(world, assetManager, neighTracker, random)

		new DelegateClosure(to:factory).call(commands)
		world.entities.add(factory.vehicle)
		return factory.vehicle
	}

	
	/** create a new vehicle  group (World) to allow group behaviours, allows diferentation between groups and a peformance gain at the same time.
	 * It is a bit abstract but fairly clever*/
	World group(Closure commands){
		WorldFactory child = new WorldFactory(assetManager, random)
		WorldFactory temp = new WorldFactory(assetManager, random)
		
		// I used to do this with delegate closure, But that did not work for some reason
		temp.world = world
		temp.neighTracker = neighTracker
		world = child.world
		neighTracker = child.neighTracker

        make(commands)
		
		world = temp.world
		neighTracker = temp.neighTracker

		// if the child world updates we also should
		world.shouldUpdate = world.shouldUpdate ?: child.world.shouldUpdate
		world.entities.add(child.world)
		world.node.attachChild(child.world.node)

		return child.world
	}

	@Override
	Spatial getSpatial() {
		world.node
	}

}
