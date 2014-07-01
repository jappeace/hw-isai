package nl.jappieklooster.ISAI.world.mortal.attack

import com.jme3.scene.Spatial;

import nl.jappieklooster.ISAI.world.Group
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
		attackGroup.members.add(attack)
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

	/**
	 * update is done by group hierachy so we can just ignore it
	 */
	@Override
	public void update(float tpf) {}

	@Override
	public Vector3 getPosition() {
		return attackGroup.position
	}

	@Override
	public Spatial getSpatial() {
		return attackGroup.spatial
	}

}
