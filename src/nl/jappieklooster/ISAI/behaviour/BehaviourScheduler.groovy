package nl.jappieklooster.ISAI.behaviour

/**
 * behaviours inside this behaviour will be completed and then removed
 * this is necisary for compositions like A-star, which is a bunch of seeks on run-time
 * @author jappie
 *
 */
class BehaviourScheduler extends AbstractBehaviour{

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
			current = behaviours.peekFirst()
			if(current == null){
				return
			}
		}
		current.execute()
	}
	
	void add(ICompletable element){
		// add is by default addlast, but order is importand so i'm explecit
		behaviours.addLast(element)
	}
	
}
