package nl.jappieklooster.ISAI.world.mortal.weapon

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.ISAI.world.World
import nl.jappieklooster.ISAI.world.entity.MovingEntity
import nl.jappieklooster.ISAI.world.mortal.AmmoFactory;
import nl.jappieklooster.ISAI.world.mortal.IAttack;
import nl.jappieklooster.ISAI.world.mortal.IWeapon;
import nl.jappieklooster.ISAI.world.mortal.Team
import nl.jappieklooster.ISAI.world.mortal.attack.Bullet
import nl.jappieklooster.math.vector.Vector3

class Pistol extends AWeapon implements IWeapon{
	
	
	private static final float force = 4000

	@Override
	protected IAttack createAttack(IPositionable target) {
		Bullet result = ammoFactory.createBullet{
			location weaponOwner.position
			scale 0.3f
		}
		
		Vector3 direction = (target.position - weaponOwner.position).normalized

		result.body.position
		result.body.force = direction * new Vector3(force)
		result.target = targetTeam

		return result;
	}
}
