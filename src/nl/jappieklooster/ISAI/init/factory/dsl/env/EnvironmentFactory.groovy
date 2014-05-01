package nl.jappieklooster.ISAI.init.factory.dsl.env

import com.jme3.asset.AssetManager

import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.TerrainNavGraphFactory
import nl.jappieklooster.ISAI.init.factory.dsl.AHasNodeFactory
import nl.jappieklooster.ISAI.init.factory.dsl.ASpatialFactory;
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
class EnvironmentFactory extends AHasNodeFactory{

	Environment environment

	EnvironmentFactory(){

		super()
		environment = new Environment()
	}
	
	TerrainQuad terrain(Closure commands){
		TerrainFactory factory = new TerrainFactory(assetManager)
		factory.environment = environment
		factory.setToDefault()
		new DelegateClosure(to:factory).call(commands)

		TerrainQuad result = factory.create(game.camera)

		TerrainNavGraphFactory navfac = new TerrainNavGraphFactory()
		navfac.terrain = result
		navfac.addRasterVerteciToGraph(20)
		
		environment.navGraph = navfac.graph
		
		return result
	}
	Spatial sky(Closure commands){
		SkyFactory factory = new SkyFactory(environment)
		factory.assetManager = assetManager
		new DelegateClosure(to:factory).call(commands)
		factory.create()
	}

	@Override
	protected AHasNode getAHasNode() {
		return environment
	}

	@Override
	protected AHasNodeFactory createChildFactory() {
		return new EnvironmentFactory()
	}

	@Override
	protected void integrateChildFactory(AHasNodeFactory child) {
	}
}
