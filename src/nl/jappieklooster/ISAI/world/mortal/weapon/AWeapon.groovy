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

abstract class AWeapon implements IWeapon{

	AmmoFactory ammoFactory
	Team targetTeam
}
