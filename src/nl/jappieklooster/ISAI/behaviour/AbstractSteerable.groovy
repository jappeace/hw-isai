package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3

/** classes that require acces to the entity should extend from this one, adds a default impl for set entity and stores it in to a varialbe named entity*/
abstract class AbstractSteerable implements ISteerable{

	/**
	 * private to force apply force
	 */
	private MovingEntity entity
	/**
	 *  the power of the behavior
	 *  */
	float chance
	
	/** 
	 * saves object creation for everey apply force
	 */
	private Vector3 chanceForce
	
	void setChance(float to){
		chance = to
		chanceForce = new Vector3(to)
	}

	public void setEntity(MovingEntity to) {
		entity = to
		chance = 1
	}
	protected void applyForce(Vector3 force){
		// correct for not being executed enough
		entity.force /= chanceForce
	}
}
