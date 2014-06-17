package nl.jappieklooster.ISAI.behaviour.change

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour
import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.behaviour.ICompletableBehaviour
import nl.jappieklooster.ISAI.world.entity.BehavingEntity
import nl.jappieklooster.ISAI.world.entity.IBehaviourEditor

/**
 * a behaviour that lets an vehicle know that target's behaviour should be modified
 * 
 * this is a behaviour so it will be executed on next cycle.
 * 
 * @author jappie
 *
 */
class ChangeBehaviour extends AbstractBehaviour implements ICompletableBehaviour{

	/**
	 * when execute is called once this behaviour is marked as done
	 */
	private boolean isDone = false
	
	/**
	 * the listener will be notified that the target behaviour is invalid
	 */
	BehavingEntity listener
	
	/**
	 * the change that will be applied
	 */
	IBehaviourEditor change
	
	ChangeBehaviour(BehavingEntity listener, IBehaviourEditor target){
		this.listener = listener
		this.change = target
	}

	@Override
	public void execute() {
		listener.behaviourChanges.add(change)
		if(listener.behaviours.contains(this)){
			listener.behaviourChanges.add(new RemoveBehaviour(this))
		}
		isDone = true
	}
	
	@Override
	public boolean isDone() {
		return isDone;
	}

}