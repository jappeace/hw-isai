package nl.jappieklooster.ISAI.init.factory.dsl.group

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.DelegateClosure;
import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.factory.dsl.AHasNodeFactory
import nl.jappieklooster.ISAI.init.factory.dsl.AMovingEntityFactory
import nl.jappieklooster.ISAI.init.factory.dsl.ASpatialFactory;
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Environment
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.Character
import nl.jappieklooster.ISAI.world.entity.BehavingEntity;
import nl.jappieklooster.ISAI.world.entity.MovingEntity
import nl.jappieklooster.ISAI.world.entity.tracking.ClickablesTracker
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.ISAI.world.mortal.weapon.Pistol
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
	/**
	 * creates a group factory without creating the group and neighbour tracker
	 * use this to add stuff to an existing group with the dsl
	 */
	GroupFactory(){}
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
		BehavingEntity result = callBehavingFactroy(new BehavingEntityFactory(neighTracker), commands)
		group.members.add(result)
		return result
	}
	
	Character character(Closure commands){
		Character result = new Character(environment.navGraph)
		
		BehavingEntityFactory factory = new BehavingEntityFactory(neighTracker)

		result.body = callBehavingFactroy(factory, commands)
		group.members.add(result)
		return result
	}
	
	/**
	 * a moving entity without behaviours to steer it is just a projectile
	 * @param commands
	 * @return
	 */
	MovingEntity projectile(Closure commands){
		MovingEntityFactory factory = new MovingEntityFactory()
		bindMovingEntityFactory(factory)
		new DelegateClosure(to: factory).call(commands)
		group.members.add(factory.movingEntity)
		group.shouldUpdate = true
		return factory.movingEntity
	}

	private BehavingEntity callBehavingFactroy(BehavingEntityFactory factory, Closure commands){
		factory.clickTracker = clickTracker
		bindMovingEntityFactory(factory)

		new DelegateClosure(to:factory).call(commands)


		return factory.behavingEntity
	}
	private void bindMovingEntityFactory(AMovingEntityFactory factory){
		factory.group = group
		factory.assetManager = assetManager
		factory.random = random
		factory.setToDefault()
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
