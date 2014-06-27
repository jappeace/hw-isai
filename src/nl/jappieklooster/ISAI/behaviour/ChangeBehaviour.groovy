package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.world.entity.BehavingEntity
import nl.jappieklooster.ISAI.collection.ICollectionEditor
import nl.jappieklooster.ISAI.collection.RemoveFromCollection
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
	ICollectionEditor<IBehaviour> change
	
	ChangeBehaviour(BehavingEntity listener, ICollectionEditor<IBehaviour> target){
		this.listener = listener
		this.change = target
	}

	@Override
	public void execute() {
		listener.behaviourChanges.add(change)
		if(listener.behaviours.contains(this)){
			listener.behaviourChanges.add(new RemoveFromCollection<IBehaviour>(this))
		}
		isDone = true
	}
	
	@Override
	public boolean isDone() {
		return isDone;
	}

}
