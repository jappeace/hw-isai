package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager
import nl.jappieklooster.ISAI.World

class WorldFactory extends AbstractFactory{
	
	WorldFactory(AssetManager manager){
		assetManager = manager
		world = new World()
		world.node = new Node("rootnode of world: " + System.identityHashCode(world))
	}
	
	World make(Closure commands){
		WorldFactory factory = new WorldFactory()
		new DelegateClosure(to: factory).call(commands)
		return factory.world
	}
	
	void vehicle(Closure commands){
		VehicleFactory factory = new VehicleFactory(world, assetManager)
		new DelegateClosure(to:factory).call(commands)
		world.entities.add(factory.vehicle)
	}
}
