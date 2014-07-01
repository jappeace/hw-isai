package nl.jappieklooster.ISAI.world.mortal.weapon

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.ISAI.world.mortal.IAttack;
import nl.jappieklooster.ISAI.world.mortal.attack.AttackCollection
import nl.jappieklooster.math.vector.*

class Shotgun extends AWeapon{

	private Random random
	private static final float power = 2000
	int maxGrainCount = 40
	float maxGrainDirectionalForce = 100
	Shotgun(){
		super()
		reloadTime = 4
		random = new Random()
	}
	@Override
	protected IAttack createAttack(IPositionable target) {
		AttackCollection result = ammoFactory.createHail(random.nextInt(maxGrainCount)) {
			// generate random spread
			force new Vector3(
				random.nextFloat() * maxGrainDirectionalForce, 
				random.nextFloat() * maxGrainDirectionalForce, 
				random.nextFloat() * maxGrainDirectionalForce
            )
			scale 0.2f
		}
		
		// place ammo on right position
		result.spatial.setLocalTranslation(Converter.toJME(weaponOwner.position))
		result.force = toDirection(target) * new Vector3(power)
		result.target = targetTeam
		return result
	}



}
