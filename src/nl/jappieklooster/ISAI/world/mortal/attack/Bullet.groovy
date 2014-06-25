package nl.jappieklooster.ISAI.world.mortal.attack

import nl.jappieklooster.ISAI.world.mortal.IMortal
import nl.jappieklooster.math.vector.Vector3

class Bullet extends AAttack{
	float damage = 100
	float decay = 0.1
	private boolean hasHit = false

	@Override
	boolean isDone() {
		return hasHit
	}

	@Override
	void update(float tpf) {
		if(hasHit){
			throw new Exception("updating a bullet that has already hit, this is probably a memory leak")
		}
		damage -= decay * tpf
		
		body.update(tpf)
		IMortal victim = findCollidingMortal()
		
		if(victim == null){
			return
		}
		
		victim.health -= damage
		hasHit = true
		
	}

}
