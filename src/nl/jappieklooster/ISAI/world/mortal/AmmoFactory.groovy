package nl.jappieklooster.ISAI.world.mortal

import nl.jappieklooster.ISAI.init.factory.dsl.group.GroupFactory
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.IHasNode
import nl.jappieklooster.ISAI.world.World
import nl.jappieklooster.ISAI.world.mortal.attack.AttackCollection
import nl.jappieklooster.ISAI.world.mortal.attack.Bullet;
import nl.jappieklooster.math.vector.Vector3

/**
 * ammo has a set of dependecies which are difficult to come by
 * this class keeps track of them so that ammo can easaly be created
 */
class AmmoFactory {

	private IHasNode environment
	
	private GroupFactory groupFactory

	private AttackCleaner attackCleaner
	
	AmmoFactory(GroupFactory factory, IHasNode env){
		groupFactory = factory
		environment = env
		init()
	}
	
	AmmoFactory(AmmoFactory src){
		groupFactory = new GroupFactory(src.groupFactory)
		environment = src.env
		init()
	}
	
	private void init(){
		attackCleaner = new AttackCleaner()
		attackCleaner.ammoGroup = groupFactory.group
		// clear default listeners
		groupFactory.group.listeners.clear()
		
		// add own listener
		groupFactory.group.listeners.add(attackCleaner)
	}
	Bullet createBullet(Closure commands){
		Bullet result = new Bullet()
		result.body = groupFactory.projectile commands

		Group group = groupFactory.group
		group.shouldUpdate = true


		// replace the added body with the result
		// use index based remove for speed (linkedlist implementation will start from the behind if index is bigger then half,
		// so it will find it directly)
		group.members.remove(group.members.size() - 1)
		group.members.add(result)

		updateCleaner(result)
		result.environment = environment
		return result
	}
	
	AttackCollection createHail(int amount, Closure commands){
		AmmoFactory childFactory = new AmmoFactory(this)
		group.attach(childFactory.group)
		
		AttackCollection result = new AttackCollection(childFactory.group)
		
		[0..amount].each{
			result.add(childFactory.createBullet(commands))
		}
		updateCleaner(result)
		return result
	}

	
	void updateCleaner(IAttack with){
		attackCleaner.attacks.add(with)
	}
	
	/**
	 * shorthand
	 * @return
	 */
	private Group getGroup(){
		return groupFactory.group
	}

}
