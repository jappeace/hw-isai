package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Vehicle;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

import com.jme3.scene.Node
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad

/**
 * primary factory that delegates tasks to all other factory,
 * it is also a big encapsulation unit since it knows game but lets it little worker factories only know what they need
 * (usaly those things are 
 * @author jappie
 *
 */
class GroupFactory extends ASpatialFactory{

	private Game game
	
	/**
	 * is used so much
	 * @return
	 */
	private AssetManager getAssetManager(){ game.assetManager }
	void setGame(Game to){
		game = to
	}

    private ScheduledThreadPoolExecutor threadPool
	GroupFactory(ScheduledThreadPoolExecutor exec){
		super(new NeighbourTracker(exec))
		threadPool = exec
		world = new World()

		world.listeners.add(neighTracker)

		neighTracker.world = world
	}
	
	/** create a new vehicle */
	Vehicle vehicle(Closure commands){
		VehicleFactory factory = new VehicleFactory(neighTracker)
		factory.world = world
		factory.assetManager = assetManager
		factory.random = random
		factory.setToDefault()

		new DelegateClosure(to:factory).call(commands)
		world.entities.add(factory.vehicle)
		return factory.vehicle
	}

	
	/** create a new vehicle  group (World) to allow group behaviours, allows diferentation between groups and a peformance gain at the same time.
	 * It is a bit abstract but fairly clever*/
	World group(Closure commands){
		GroupFactory child = new GroupFactory(threadPool)
		
		child.game = game
		child.random = random
		new DelegateClosure(to: child).call(commands)
		
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
	
	TerrainQuad terrain(Closure commands){
		TerrainFactory factory = new TerrainFactory(assetManager)
		factory.world = world
		factory.setToDefault()
		new DelegateClosure(to:factory).call(commands)
		factory.create(game.camera)
	}

	Spatial sky(Closure commands){
		SkyFactory factory = new SkyFactory(world)
		factory.assetManager = assetManager
		new DelegateClosure(to:factory).call(commands)
		factory.create()
	}
	

}
