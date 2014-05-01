package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.TerrainNavGraphFactory
import nl.jappieklooster.ISAI.init.factory.dsl.env.EnvironmentFactory
import nl.jappieklooster.ISAI.init.factory.dsl.group.GroupFactory;
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Environment
import nl.jappieklooster.ISAI.world.Group
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
class WorldFactory extends AHasNodeFactory{

	World world

    private ScheduledThreadPoolExecutor threadPool

	WorldFactory(ScheduledThreadPoolExecutor exec){
		threadPool = exec
		world = new World()
	}
	
	/**
	 * creates the enviroinmant, after the commands are executed the navigation graph will be created
	 * @param commands
	 * @return
	 */
	Environment environment(Closure commands){
		EnvironmentFactory factory = new EnvironmentFactory()
		factory.game = game
		
		new DelegateClosure(to: factory).call(commands)
		
		world.environment = factory.environment
		world.node.attachChild(factory.environment.node)
		return factory.environment
	}

	@Override
	protected AHasNode getAHasNode() {
		return world
	}

	@Override
	protected AHasNodeFactory createChildFactory() {
		GroupFactory factory = new GroupFactory(threadPool)
		
		factory.random = new Random()
		return factory
	}

	@Override
	protected void integrateChildFactory(AHasNodeFactory child) {
		
		world.actors.add(((GroupFactory)child).group)
	}
	
	

}
