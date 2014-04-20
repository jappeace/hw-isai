package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3

/** classes that require acces to the entity should extend from this one, adds a default impl for set entity and stores it in to a varialbe named entity*/
abstract class AbstractSteerable implements ISteerable{

	MovingEntity entity
	/**
	 *  the power of the behavior
	 *  */
	Vector3 power

	public void setEntity(MovingEntity to) {
		entity = to
		power = new Vector3(1)
	}
}
