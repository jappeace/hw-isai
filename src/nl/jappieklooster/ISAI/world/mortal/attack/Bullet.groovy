package nl.jappieklooster.ISAI.world.mortal.attack

import com.jme3.scene.Spatial;

import nl.jappieklooster.ISAI.world.mortal.IMortal
import nl.jappieklooster.math.vector.Vector3

class Bullet extends AAttack{
	float damage = 100
	float decay = 5
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
		
		if(damage < 0){
			hasHit = true
			return
		}
		body.update(tpf)
		IMortal victim = findCollidingMortal()
		
		if(victim == null){
			return
		}
		
		victim.health -= damage
		hasHit = true
		
	}



}
