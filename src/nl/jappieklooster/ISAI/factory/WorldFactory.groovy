package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager

import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.entity.impl.Vehicle
import nl.jappieklooster.ISAI.entity.tracking.NeighbourTracker
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

import com.jme3.scene.Node
import com.jme3.scene.Spatial;

class WorldFactory extends SpatialFactory{

	AssetManager assetManager
	WorldFactory(AssetManager manager){
		super(new World(), new NeighbourTracker())
		assetManager = manager

		world.node = new Node("rootnode of world: " + System.identityHashCode(world))
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
		VehicleFactory factory = new VehicleFactory(world, assetManager, neighTracker)

		new DelegateClosure(to:factory).call(commands)
		world.entities.add(factory.vehicle)
		return factory.vehicle
	}

	
	/** create a new vehicle  group (World) to allow group behaviours, allows diferentation between groups and a peformance gain at the same time.
	 * It is a bit abstract but fairly clever*/
	World group(Closure commands){
		WorldFactory child = new WorldFactory(assetManager)
		new DelegateClosure(to:child).call(commands)

		world.entities.add(child.world)
		world.node.attachChild(child.world.node)

		return child.world
	}

	@Override
	public Spatial getSpatial() {
		world.node
	}
}
