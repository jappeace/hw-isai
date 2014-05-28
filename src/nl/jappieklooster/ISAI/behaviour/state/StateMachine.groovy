package nl.jappieklooster.ISAI.behaviour.state
import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
class StateMachine extends AbstractBehaviour{

	IState currentState = null

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
