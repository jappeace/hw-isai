package nl.jappieklooster.ISAI.init.factory.dsl.env

import nl.jappieklooster.ISAI.init.factory.dsl.AEntityFactory
import nl.jappieklooster.ISAI.world.AHasNode
import nl.jappieklooster.ISAI.world.entity.Entity
import nl.jappieklooster.math.vector.Vector3


class ObstacleFactory extends AEntityFactory{
	Entity obstacle

	/**
	 * keep it abstract so that both environment and group can call it
	 */
	AHasNode parent
	ObstacleFactory(){

		obstacle = new Entity()
		obstacle.position = new Vector3()
	}
	@Override
	public Entity getEntity() {
		return obstacle
	}
	@Override
	public AHasNode getNodeContainer() {
		return parent
	}
}
