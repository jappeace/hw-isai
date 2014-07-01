package nl.jappieklooster.ISAI.world.mortal.weapon

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.ISAI.world.mortal.IAttack;
import nl.jappieklooster.ISAI.world.mortal.attack.AttackCollection
import nl.jappieklooster.math.vector.Vector3

class Shotgun extends AWeapon{

	private Random random
	int maxGrainCount = 20
	float maxGrainDirectionalForce = 100
	Shotgun(){
		random = new Random()
	}
	@Override
	protected IAttack createAttack(IPositionable target) {
		AttackCollection result = ammoFactory.createHail(random.nextInt(maxGrainCount)) {
			force new Vector3(
				random.nextFloat() * maxGrainDirectionalForce, 
				random.nextFloat() * maxGrainDirectionalForce, 
				random.nextFloat() * maxGrainDirectionalForce
            )
			location weaponOwner.position
			scale 0.3f
		}
	}



}
