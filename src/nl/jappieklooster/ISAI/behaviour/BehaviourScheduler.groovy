package nl.jappieklooster.ISAI.behaviour

/**
 * behaviours inside this behaviour will be completed and then removed
 * this is necisary for compositions like A-star, which is a bunch of seeks on run-time
 * 
 * this does not violate the contract that behaviours should do one thing, because the only
 * thing this behaviour does is determening which behaviours should go next in the queue
 * @author jappie
 *
 */
class BehaviourScheduler extends AbstractBehaviour implements ICompletable{

	Deque<ICompletableBehaviour> behaviours
    BehaviourScheduler(){
		behaviours = new LinkedList<>()
	}
	@Override
	void execute() {
		ICompletableBehaviour current = behaviours.peekFirst()
		if(current == null){
			return
		}
		if(current.isDone()){
			behaviours.pollFirst();
			execute()
		}
		current.execute()
	}
	
	void add(ICompletableBehaviour element){
		// add is by default addlast, but order is importand so i'm explecit
		behaviours.addLast(element)
	}
	@Override
	public boolean isDone() {
		if(behaviours.size() == 0){
			return true
		}
		if(behaviours.peekFirst() == null){
			return true
		}
		// their might be just a cleanup active
		if(behaviours.size() != 1){
            return false
		}

		return behaviours.peekFirst().isDone()
	}
	
}
