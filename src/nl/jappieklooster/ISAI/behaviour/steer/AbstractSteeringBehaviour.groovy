package nl.jappieklooster.ISAI.behaviour.steer

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour
import nl.jappieklooster.ISAI.world.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3

/** classes that require acces to the entity should extend from this one, adds a default impl for set entity and stores it in to a varialbe named entity*/
abstract class AbstractSteeringBehaviour extends AbstractBehaviour implements ISteeringBehaviour{

	/**
	 * private to force apply force
	 */
	MovingEntity entity

}
