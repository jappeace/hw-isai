package nl.jappieklooster.ISAI.init.factory.dsl

import nl.jappieklooster.ISAI.init.factory.dsl.group.GroupFactory
import nl.jappieklooster.ISAI.world.Environment
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.mortal.AmmoFactory
import nl.jappieklooster.ISAI.world.mortal.IWeapon
import nl.jappieklooster.ISAI.world.mortal.Team
import nl.jappieklooster.ISAI.world.mortal.weapon.Pistol

class WeaponFactory {

	private AmmoFactory ammoFactory
	Team targetTeam
	WeaponFactory(GroupFactory ammoGroupFactory, Environment environment){
		ammoFactory = new AmmoFactory()
		ammoFactory.groupFactory = ammoGroupFactory
		ammoFactory.environment = environment
	}
	
	IWeapon pistol(IPositionable owner){
		return new Pistol(ammoFactory: ammoFactory, targetTeam:targetTeam, pistolOwner:owner)
	}
}

