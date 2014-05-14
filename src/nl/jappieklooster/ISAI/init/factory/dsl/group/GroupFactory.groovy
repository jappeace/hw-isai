package nl.jappieklooster.ISAI.init.factory.dsl.group

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.AHasNodeFactory
import nl.jappieklooster.ISAI.init.factory.dsl.AMovingEntityFactory
import nl.jappieklooster.ISAI.init.factory.dsl.ASpatialFactory;
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.entity.Vehicle;
import nl.jappieklooster.ISAI.world.entity.tracking.ClickablesTracker
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
class GroupFactory extends AHasNodeFactory{

	Group group
	NeighbourTracker neighTracker
	Random random
	ClickablesTracker clickTracker

    private ScheduledThreadPoolExecutor threadPool
	GroupFactory(ScheduledThreadPoolExecutor exec){
		exec.corePoolSize += 1
		neighTracker = new NeighbourTracker(exec)
		threadPool = exec
		group = new Group()

		group.listeners.add(neighTracker)

		neighTracker.group = group
	}

	/** create a new vehicle */
	Vehicle vehicle(Closure commands){
		VehicleFactory factory = new VehicleFactory(neighTracker)
		factory.clickTracker = clickTracker
		delegateMovingFactory(factory, commands)
		return factory.vehicle
	}

	@Override
	protected AHasNode getAHasNode() {
		return group
	}

	@Override
	protected AHasNodeFactory createChildFactory() {
		GroupFactory child = new GroupFactory(threadPool)
		child.random = random
		return child
	}

	@Override
	protected void integrateChildFactory(AHasNodeFactory child) {
		GroupFactory factory = (GroupFactory) child
		group.shouldUpdate = group.shouldUpdate ?: factory.group.shouldUpdate
		group.entities.add(factory.group)
	}
	
	
	private void delegateMovingFactory(AMovingEntityFactory factory, Closure commands){
		factory.group = group
		factory.assetManager = assetManager
		factory.random = random
		factory.setToDefault()

		new DelegateClosure(to:factory).call(commands)
		group.entities.add(factory.movingEntity)
	}

}
