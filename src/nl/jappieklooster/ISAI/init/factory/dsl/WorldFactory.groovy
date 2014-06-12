package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.TaskSynchronizer;
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.env.EnvironmentFactory
import nl.jappieklooster.ISAI.init.factory.dsl.env.LightFactory
import nl.jappieklooster.ISAI.init.factory.dsl.group.GroupFactory;
import nl.jappieklooster.ISAI.init.factory.path.TerrainNavGraphFactory;
import nl.jappieklooster.ISAI.world.AHasNode;
import nl.jappieklooster.ISAI.world.Environment
import nl.jappieklooster.ISAI.world.Group
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
 * primary factory that delegates tasks to all other factory,
 * it is also a big encapsulation unit since it knows game but lets it little worker factories only know what they need
 * (usaly those things are 
 * @author jappie
 *
 */
class WorldFactory extends AHasNodeFactory{

	World world

	private ClickablesTracker clickTracker
    private ScheduledThreadPoolExecutor threadPool
	/**
	 * allows levelloader check if default lighting is necisary
	 */
	boolean lightAttached = false

	WorldFactory(ScheduledThreadPoolExecutor exec, ClickablesTracker clickables){
		threadPool = exec
		world = new World()
		this.clickTracker = clickables
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
		
		TerrainNavGraphFactory navfac = new TerrainNavGraphFactory()

		navfac.graph = factory.environment.navGraph
		navfac.collidable.attachChild(factory.environment.node.clone())
        navfac.connectVerticiCloserThenAsync(30, new TaskSynchronizer(application: game))

		world.environment = factory.environment
		world.node.attachChild(factory.environment.node)
		return factory.environment
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
		GroupFactory factory = new GroupFactory(threadPool)
		factory.clickTracker = clickTracker
		factory.random = new Random()
		return factory
	}

	@Override
	protected void integrateChildFactory(AHasNodeFactory child) {
		
		world.actors.add(child.AHasNode)
	}
	
	void setDefaultsIfNecisary(){
		
		if(!lightAttached){
            //ambient light
            light{
                color = ColorRGBA.White.mult(1.5)
            }
            //directional 1
            light {
                color = ColorRGBA.Red.mult(0.4)
                direction = new Vector3(1,-0.5,-2.8).normalized
            }

            //directional 2
            light{
                color = ColorRGBA.Green.mult(0.4)
                direction = new Vector3(-1,-1,-2.8).normalized
            }
		}
	}
	
	

}
