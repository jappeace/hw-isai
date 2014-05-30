package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.world.entity.Vehicle

/**
 * a behaviour that lets an vehicle know that target behaviour should be removed
 * @author jappie
 *
 */
class Invalidator extends AbstractBehaviour implements ICompletableBehaviour{

	/**
	 * if this invalidator is in the target vehicle's behaviour list
	 * it will mark itself as invalid after marking the target behaviour as invalid
	 * 
	 * if the invalidator is not in the vehicle's list nothing will happen so its safe to keep this on true
	 */
	boolean shouldInvalidateSelf = true
	
	/**
	 * when execute is called once this behaviour is marked as done
	 */
	private boolean done = false
	
	/**
	 * the listener will be notified that the target behaviour is invalid
	 */
	Vehicle listener
	
	/**
	 * this behaviour will be marked invalid
	 */
	IBehaviour target
	
	Invalidator(Vehicle listener, IBehaviour target){
		this.listener = listener
		this.target = target
	}

	@Override
	public void execute() {
		if(!listener.behaviours.contains(target)){
			throw new IllegalArgumentException("tried to mark a nonexsisting behaviour as invalid")
		}
		listener.invalidatedBehaviours.add(target)
		done = true

		if(!shouldInvalidateSelf){
			return
		}
		if(listener.behaviours.contains(this)){
			listener.invalidatedBehaviours.add(this)
		}
	}
	@Override
	public boolean isDone() {
		return done;
	}

}
