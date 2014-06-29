package nl.jappieklooster.ISAI.world.mortal.attack

import com.jme3.scene.Spatial;

import nl.jappieklooster.ISAI.world.mortal.IAttack
import nl.jappieklooster.math.vector.Vector3;
import nl.jappieklooster.ISAI.Util

/**
 * if an attack is actualy one bunch of attacks use this class
 * for example a shotgun fires a bunch of little projectils, this class andles that
 * @author jappie
 *
 */
class AttackCollection implements IAttack{
	
	Collection<IAttack> attacks
	AttackCollection(){
		attacks = new LinkedList<>()
	}

	@Override
	public boolean isDone() {
		for(IAttack attack : attacks){
			if(!attack.isDone()){
				return false
			}
		}
		return true
	}

	@Override
	public void update(float tpf) {
		attacks.each{
			it.update(tpf)
		}
		
	}

	@Override
	public Vector3 getPosition() {
		return Util.averageOfPositionables(attacks)
	}

	@Override
	public Spatial getSpatial() {
		return attacks.first().spatial
	}

}
