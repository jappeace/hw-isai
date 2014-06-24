package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.TaskSynchronizer;
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.env.EnvironmentFactory
import nl.jappieklooster.ISAI.init.factory.dsl.env.LightFactory
import nl.jappieklooster.ISAI.init.factory.dsl.group.GroupFactory;
import nl.jappieklooster.ISAI.init.factory.dsl.group.TeamFactory
import nl.jappieklooster.ISAI.init.factory.path.TerrainNavGraphFactory;
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Environment
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.Team
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.BehavingEntity;
import nl.jappieklooster.ISAI.world.entity.tracking.ClickablesTracker
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad

import com.jme3.math.ColorRGBA

/**
 * primary factory that delegates tasks to all other factories,
 * it is also a big encapsulation unit since it knows game but lets it little worker factories only know what they need
 */
class WorldFactory extends AHasNodeFactory{

	World world

	private ClickablesTracker clickTracker
    private ScheduledThreadPoolExecutor threadPool
	
	/**
	 * keeping track of the environment factory allows the environment calls to be above or below the group calls
	 * because the enviornment only creates an environment on its own creation.
	 * 
	 * if I just the environment in the word all the entities that require a environment needed to be created after
	 * the environment was created
	 * because the world sets its environment to null and waits till its added later. keeping track of the env factory
	 * circumvents the problem
	 */
	private EnvironmentFactory environmentFactory
	/**
	 * allows levelloader check if default lighting is necisary
	 */
	boolean lightAttached = false
	
	/**
	 * randomness is shared amongst all children
	 */
	private Random random

	WorldFactory(ScheduledThreadPoolExecutor exec, ClickablesTracker clickables){
		threadPool = exec
		world = new World()
		this.clickTracker = clickables
		environmentFactory = new EnvironmentFactory()
		random = new Random()
	}
	
	/**
	 * creates the enviroinmant, after the commands are executed the navigation graph will be created
	 * @param commands
	 * @return
	 */
	Environment environment(Closure commands){
		environmentFactory.game = game
		
		new DelegateClosure(to: environmentFactory).call(commands)
		
		TerrainNavGraphFactory navfac = new TerrainNavGraphFactory()

		navfac.graph = environmentFactory.environment.navGraph
		navfac.collidable.attachChild(environmentFactory.environment.node.clone())
        navfac.connectVerticiCloserThenAsync(30, new TaskSynchronizer(application: game))

		world.environment = environmentFactory.environment
		world.node.attachChild(environmentFactory.environment.node)
		return environmentFactory.environment
	}

	Team team(Closure commands){
        TeamFactory factory = bindGroupFactory(new TeamFactory(threadPool))
		factory.game = game

		new DelegateClosure(to: factory).call(commands)

		world.node.attachChild(factory.team.node)
		world.actors.add(factory.team)
		factory.team.rivalTeams.each{Team team ->
			world.node.attachChild(team.node)
			world.actors.add(team)
		}
		return factory.team
	}
	void light(Closure commands){
		lightAttached = true
		LightFactory lightFactory = new LightFactory()
		new DelegateClosure(to:lightFactory).call(commands)
		world.node.addLight(lightFactory.light)
	}

	@Override
	protected AHasNode getAHasNode() {
		return world
	}

	@Override
	protected AHasNodeFactory createChildFactory() {
		return bindGroupFactory(new GroupFactory(threadPool))
	}

	@Override
	protected void integrateChildFactory(AHasNodeFactory child) {
		
		world.actors.add(child.AHasNode)
	}

	private GroupFactory bindGroupFactory(GroupFactory factory){
		factory.environment = environmentFactory.environment
		factory.clickTracker = clickTracker
		factory.random = random
		return factory
	}
}
