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
	
	/**
	 * in seconds the reload time
	 */
	protected float reloadTime
	
	protected float timeSinceLastAttack
	
	AWeapon(){
		reloadTime = 1
        timeSinceLastAttack = 0
	}
	
	@Override
	IAttack attack(IPositionable target) {
		if(timeSinceLastAttack < reloadTime){
			return null
		}
		timeSinceLastAttack = 0
		return createAttack(target)
	}
	
	protected abstract createAttack(IPositionable target)
	
	void update(float tpf){
		timeSinceLastAttack += tpf
	}
}
