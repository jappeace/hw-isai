package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager
import com.jme3.scene.Spatial;
import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.DelegateClosure
import nl.jappieklooster.ISAI.init.factory.dsl.env.ObstacleFactory
import nl.jappieklooster.ISAI.world.AHasNode
import nl.jappieklooster.ISAI.world.entity.Entity

/**
 * should be AAHasnodeFactory but that is just silly
 * this factory contains commands for both environment and group
 * @author jappie
 *
 */
abstract class AHasNodeFactory extends ASpatialFactory{


	/**
	 * the factories that have a node are usaly very high in the command chain,
	 * they pass more specialized objects to other factories, keeping track of each separatly is better
	 * encapsoulated, but also very tedious
	 */
	protected Game game
	/**
	 * is used a lot, this is shorter and more dry
	 * @return
	 */
	protected AssetManager getAssetManager(){ game.assetManager }
	/**
	 * write only game, its to dangerous to expose directly to dsl
	 * @param to
	 */
	void setGame(Game to){
		game = to
	}
	

	Entity obstacle(Closure commands){
		ObstacleFactory factory = new ObstacleFactory()
		factory.parent = AHasNode
		factory.assetManager = assetManager
		factory.setToDefault()
		new DelegateClosure(to:factory).call(commands)
		return factory.obstacle
	}

	/**
	 * groups stuff together by injecting a child factory as delagate for the commands
	 * after that the child factory's node gets attached to this node
	 * 
	 * allows the transformation of things as a whole, because its a group, using native jme3 concepts
	 * @param commands
	 * @return
	 */
	AHasNode group(Closure commands){
		AHasNodeFactory factory = createChildFactory()
		
		factory.game = game
		new DelegateClosure(to: factory).call(commands)
		
		AHasNode.node.attachChild(factory.AHasNode.node)

		integrateChildFactory(factory)
		return factory.AHasNode
	}

	protected abstract AHasNodeFactory createChildFactory()
	protected abstract void integrateChildFactory(AHasNodeFactory child)
	protected abstract AHasNode getAHasNode()

	/**
	 * in our case the node is meant as spatial
	 */
	@Override
	protected Spatial getSpatial() {
		return AHasNode.node
	}
}
