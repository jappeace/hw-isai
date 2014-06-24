package nl.jappieklooster.ISAI.init.factory.dsl.group

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.AHasNodeFactory
import nl.jappieklooster.ISAI.init.factory.dsl.AMovingEntityFactory
import nl.jappieklooster.ISAI.init.factory.dsl.ASpatialFactory;
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Environment
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.Character
import nl.jappieklooster.ISAI.world.entity.BehavingEntity;
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
	protected Environment environment

    protected ScheduledThreadPoolExecutor threadPool
	GroupFactory(ScheduledThreadPoolExecutor exec){
		threadPool = exec
		init()
	}
	/**
	 * copies all external information of the other group factory
	 * but still creates its own group to work with
	 * @param source
	 */
	GroupFactory(GroupFactory source){
		game = source.game
		random = source.random
		clickTracker = source.clickTracker
		environment = source.environment
		threadPool = source.threadPool
		init()
	}
	private void init(){
		threadPool.corePoolSize += 1
		neighTracker = new NeighbourTracker(threadPool)
		group = new Group()
		group.listeners.add(neighTracker)
		neighTracker.group = group
	}
	void setEnvironment(Environment to){
		environment = to
	}

	/** create a new vehicle */
	BehavingEntity vehicle(Closure commands){
		return callBehavingFactroy(new BehavingEntityFactory(neighTracker), commands)
	}
	
	Character character(Closure commands){
		Character result = new Character(environment.navGraph)
		BehavingEntityFactory factory = new BehavingEntityFactory(neighTracker)
		result.body = callBehavingFactroy(factory, commands)
		return result
	}

	private BehavingEntity callBehavingFactroy(BehavingEntityFactory factory, Closure commands){
		factory.clickTracker = clickTracker
		factory.group = group
		factory.assetManager = assetManager
		factory.random = random
		factory.setToDefault()

		new DelegateClosure(to:factory).call(commands)

		group.members.add(factory.movingEntity)

		return factory.behavingEntity
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
		group.members.add(factory.group)
	}
}
