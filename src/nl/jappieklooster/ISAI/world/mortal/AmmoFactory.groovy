package nl.jappieklooster.ISAI.world.mortal

import nl.jappieklooster.ISAI.init.factory.dsl.group.GroupFactory
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.IHasNode
import nl.jappieklooster.ISAI.world.World
import nl.jappieklooster.ISAI.world.mortal.attack.Bullet;
import nl.jappieklooster.math.vector.Vector3

/**
 * ammo has a set of dependecies which are difficult to come by
 * this class keeps track of them so that ammo can easaly be created
 */
class AmmoFactory {

	IHasNode environment
	
	GroupFactory groupFactory

	Bullet createBullet(Closure commands){
		Bullet result = new Bullet()
		result.body = groupFactory.projectile commands

		result.environment = environment
		return result
	}
}
