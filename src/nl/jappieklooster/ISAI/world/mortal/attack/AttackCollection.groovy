package nl.jappieklooster.ISAI.world.mortal.attack

import com.jme3.scene.Spatial;

import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.mortal.IAttack
import nl.jappieklooster.ISAI.world.mortal.Team
import nl.jappieklooster.math.vector.Vector3;
import nl.jappieklooster.ISAI.Util

/**
 * if an attack is actualy one bunch of attacks use this class
 * for example a shotgun fires a bunch of little projectils, this class andles that
 * @author jappie
 *
 */
class AttackCollection implements IAttack{
	
	private Collection<IAttack> attacks
	private Group attackGroup
	/**
	 * 
	 * @param attackGroup this will be used to update the attacks and to apply forces on
	 */
	AttackCollection(Group attackGroup){
		this.attackGroup = attackGroup
		attacks = new LinkedList<>()
	}
	
	
	void add(IAttack attack){
		attacks.add(attack)
	}

	@Override
	boolean isDone() {
		for(IAttack attack : attacks){
			if(!attack.isDone()){
				return false
			}
		}
		return true
	}

	/**
	 * update is done by group hierachy so we can just ignore it
	 */
	@Override
	void update(float tpf) {
		attackGroup.update(tpf)
	}

	@Override
	Vector3 getPosition() {
		return attackGroup.position
	}

	void setForce(Vector3 to){
		attackGroup.force = to
	}

	@Override
	Spatial getSpatial() {
		return attackGroup.spatial
	}

	@Override
	void setTarget(Team to){
		attacks.each{
			it.setTarget(to)
		}
	}
}
