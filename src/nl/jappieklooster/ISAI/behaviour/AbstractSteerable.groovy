package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.entity.MovingEntity

/** classes that require acces to the entity should extend from this one, adds a default impl for set entity and stores it in to a varialbe named entity*/
abstract class AbstractSteerable implements ISteerable{

	MovingEntity entity

	public void setEntity(MovingEntity to) {
		entity = to
	}
}
