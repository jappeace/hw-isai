package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.env.EnvironmentFactory
import nl.jappieklooster.ISAI.init.factory.dsl.group.GroupFactory;
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
class WorldFactory extends ASpatialFactory{

	World world

	private Game game
	/**
	 * is used so much
	 * @return
	 */
	private AssetManager getAssetManager(){ game.assetManager }
    private ScheduledThreadPoolExecutor threadPool

	WorldFactory(ScheduledThreadPoolExecutor exec){
		threadPool = exec
		world = new World()
	}
	
	void setGame(Game to){
		game = to
	}
	/** create a new vehicle  group (World) to allow group behaviours, allows diferentation between groups and a peformance gain at the same time.
	 * It is a bit abstract but fairly clever*/
	Group group(Closure commands){
		GroupFactory factory = new GroupFactory(threadPool)
		
		factory.game = game
		factory.random = new Random()
		new DelegateClosure(to: factory).call(commands)
		
		// if the child world updates we also should
		world.actors.add(factory.group)
		world.node.attachChild(factory.group.node)

		return factory.group
	}
	
	Environment environment(Closure commands){
		EnvironmentFactory factory = new EnvironmentFactory()
		factory.game = game
		
		new DelegateClosure(to: factory).call(commands)
		
		world.environment = factory.environment
		world.node.attachChild(factory.environment.node)
		return factory.environment
	}

	@Override
	Spatial getSpatial() {
		world.node
	}
	
	

}
