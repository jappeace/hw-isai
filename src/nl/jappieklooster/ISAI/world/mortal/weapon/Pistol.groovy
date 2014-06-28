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
	
	/**
	 * to avoid nameclases with a closure owner, prepended with pistol
	 */
	IPositionable pistolOwner
	
	private static final float force = 100000

	@Override
	public IAttack createAttack(IPositionable target) {
		Bullet result = ammoFactory.createBullet{
			location pistolOwner.position
		}
		
		Vector3 direction = (target.position - pistolOwner.position).normalized

		result.body.position
		result.body.force = direction * new Vector3(force)
		result.target = targetTeam

		return result;
	}
}
