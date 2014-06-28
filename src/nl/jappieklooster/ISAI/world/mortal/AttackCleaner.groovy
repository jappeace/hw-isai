package nl.jappieklooster.ISAI.world.mortal

import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.IUpdatable

class AttackCleaner implements IUpdatable{

	Group ammoGroup

	Collection<IAttack> attacks
	
	AttackCleaner(){
		attacks = new LinkedList<>()
	}
	@Override
	public void update(float tpf) {
		Iterator<IAttack> attackIt = attacks.iterator()
		while(attackIt.hasNext()){
			IAttack attack = attackIt.next()
			if(attack.isDone()){
				ammoGroup.members.remove(attack)
				ammoGroup.node.detachChild(attack.spatial)
				attackIt.remove()
			}
			
		}
	}	
}
