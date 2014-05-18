package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.world.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3

/** classes that require acces to the entity should extend from this one, adds a default impl for set entity and stores it in to a varialbe named entity*/
abstract class AbstractBehaviour implements IBehaviour{

	/**
	 * private to force apply force
	 */
	MovingEntity entity
	/**
	 *  the power of the behavior
	 *  */
	float chance
	
	public void setEntity(MovingEntity to) {
		entity = to
		chance = 1
	}
}
