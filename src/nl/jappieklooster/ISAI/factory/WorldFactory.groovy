package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager
import nl.jappieklooster.ISAI.World
import com.jme3.scene.Node

class WorldFactory extends AbstractFactory{
	
	WorldFactory(AssetManager manager){
		assetManager = manager
		world = new World()
		world.node = new Node("rootnode of world: " + System.identityHashCode(world))
		world.entities = new LinkedList<>()
	}
	
	World make(Closure commands){
		new DelegateClosure(to: this).call(commands)
		return world
	}
	
	void vehicle(Closure commands){
		VehicleFactory factory = new VehicleFactory(world, assetManager)
		new DelegateClosure(to:factory).call(commands)
		world.entities.add(factory.vehicle)
	}
}
