package nl.jappieklooster.ISAI.init.factory.dsl.group

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.ASpatialFactory;
import nl.jappieklooster.ISAI.world.Group
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

	Group group
	NeighbourTracker neighTracker
	Random random
	private Game game
	
	/**
	 * is used so much
	 * @return
	 */
	private AssetManager getAssetManager(){ game.assetManager }

    private ScheduledThreadPoolExecutor threadPool
	GroupFactory(ScheduledThreadPoolExecutor exec){
		exec.setCorePoolSize(exec.corePoolSize + 1)
		neighTracker = new NeighbourTracker(exec)
		threadPool = exec
		group = new Group()

		group.listeners.add(neighTracker)

		neighTracker.group = group
	}

	void setGame(Game to){
		game = to
	}
	
	/** create a new vehicle */
	Vehicle vehicle(Closure commands){
		VehicleFactory factory = new VehicleFactory(neighTracker)
		factory.group = group
		factory.assetManager = assetManager
		factory.random = random
		factory.setToDefault()

		new DelegateClosure(to:factory).call(commands)
		group.entities.add(factory.vehicle)
		return factory.vehicle
	}

	
	/** create a new vehicle  group (World) to allow group behaviours, allows diferentation between groups and a peformance gain at the same time.
	 * It is a bit abstract but fairly clever*/
	Group group(Closure commands){

		GroupFactory child = new GroupFactory(threadPool)
		
		child.game = game
		child.random = random
		new DelegateClosure(to: child).call(commands)
		
		// if the child group updates we also should
		group.shouldUpdate = group.shouldUpdate ?: child.group.shouldUpdate
		group.entities.add(child.group)
		group.node.attachChild(child.group.node)

		return child.group
	}

	@Override
	Spatial getSpatial() {
		group.node
	}
}
