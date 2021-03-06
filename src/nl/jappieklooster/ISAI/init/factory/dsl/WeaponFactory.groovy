package nl.jappieklooster.ISAI.init.factory.dsl

import nl.jappieklooster.ISAI.init.factory.dsl.group.GroupFactory
import nl.jappieklooster.ISAI.world.Environment
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.mortal.AmmoFactory
import nl.jappieklooster.ISAI.world.mortal.IWeapon
import nl.jappieklooster.ISAI.world.mortal.Team
import nl.jappieklooster.ISAI.world.mortal.weapon.Pistol
import nl.jappieklooster.ISAI.world.mortal.weapon.Shotgun

class WeaponFactory {

	/**
	 * the group factory containing the ammo group
	 * 
	 * the ammo group consist of groups
	 * these sub groups hold the attacks of the different weapons.
	 * 
	 * this is an optimzation in that deleting no longer relevant ammo can be done faster
	 * because the memberslists are smaller
	 */
	private GroupFactory groupFactory
	private Environment environment
	Team targetTeam
	WeaponFactory(GroupFactory ammoGroupFactory, Environment env){
		groupFactory = ammoGroupFactory
		environment = env
	}
	
	IWeapon pistol(IPositionable owner){
		return new Pistol(
			ammoFactory: createAmmo(), 
			targetTeam:targetTeam, 
			weaponOwner:owner
        )
	}
	
	IWeapon shotgun(IPositionable owner){
		return new Shotgun(
			ammoFactory: createAmmo(), 
			targetTeam:targetTeam, 
			weaponOwner:owner
		)
	}
	
	private AmmoFactory createAmmo(){
		GroupFactory subGroupFactory = new GroupFactory(groupFactory)
		groupFactory.group.shouldUpdate = true
		subGroupFactory.group = groupFactory.group{
			name "ammo group"
		}
		return new AmmoFactory(subGroupFactory, environment)
	}
}

