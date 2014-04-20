package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager

import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.entity.impl.Vehicle
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

import com.jme3.scene.Node
import com.jme3.scene.Spatial;

class WorldFactory extends SpatialFactory{
	World world
	AssetManager assetManager
	private DelegateClosure closure
	WorldFactory(AssetManager manager){
		closure = new DelegateClosure(to: this)
		assetManager = manager
		world = new World()
		world.node = new Node("rootnode of world: " + System.identityHashCode(world))
		world.entities = new LinkedList<>()
	}
	
	World make(Closure commands){
		closure.call(commands)
		return world
	}
	
	/** create a new vehicle */
	Vehicle vehicle(Closure commands){
		VehicleFactory factory = new VehicleFactory(world, assetManager)

		new DelegateClosure(to:factory).call(commands)
		world.entities.add(factory.vehicle)
		return factory.vehicle
	}

	
	/** create a new vehicle  group (World) to allow group behaviours, allows diferentation between groups and a peformance gain at the same time.
	 * It is a bit abstract but fairly clever*/
	World group(Closure commands){
		WorldFactory child = new WorldFactory(assetManager)

		child.make(commands)

		world.entities.add(child.world)
		world.node.attachChild(child.world.node)

		return child.world
	}
	World groupEach(Collection items, Closure commands){
        WorldFactory child = new WorldFactory(assetManager)
		items.each{
			child.closure.arguments = it
			child.make(commands)
		}
		world.entities.add(child.world)
		world.node.attachChild(child.world.node)
		return child.world
	}
	
	@Override
	public Spatial getSpatial() {
		world.node
	}
}
