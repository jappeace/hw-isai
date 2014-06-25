package nl.jappieklooster.ISAI.world.mortal.weapon

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.ISAI.world.mortal.IAttack;
import nl.jappieklooster.ISAI.world.mortal.IWeapon;
import nl.jappieklooster.ISAI.world.mortal.attack.Bullet

class Pistol implements IWeapon{
	

	@Override
	public IAttack createAttack(IPositionable target) {
		Bullet result = new Bullet()
		return result;
	}
}
