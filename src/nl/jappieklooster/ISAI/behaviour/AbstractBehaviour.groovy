package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.world.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3

/**
 * implements the chance getter and setter of IBehaviour
 * @author jappie
 *
 */
abstract class AbstractBehaviour implements IBehaviour{

	/**
	 *  the power of the behavior
	 *  */
	float chance
	
	AbstractBehaviour(){
		chance = 1
	}
}
