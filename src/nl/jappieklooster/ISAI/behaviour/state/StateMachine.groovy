package nl.jappieklooster.ISAI.behaviour.state
import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
class StateMachine extends AbstractBehaviour{

	IState currentState = null
	
	/**
	 * by exposing this constructer it is always possible to get the owner of a
	 * statemachine if you have the statemachine
	 * @param owner
	 */
	StateMachine(IStateMachineContainer owner){
		if(owner == null){
			// this would be a bug
			throw new IllegalArgumentException("You cannot create a state machine for a null pointer, (the owner was set to null)")
		}
		this.owner = owner
	}
	IStateMachineContainer getOwner(){
		return owner	
	}

	void setCurrentState(IState to){
		if(currentState){
			currentState.onExit()
		}
		currentState = to
		if(currentState){
			currentState.onEnter()
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if(currentState){
			currentState.execute()
		}
		
	}
}
