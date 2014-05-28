package nl.jappieklooster.ISAI.state.vehicle
import nl.jappieklooster.ISAI.world.IUpdatable
class StateMachine implements IUpdatable{

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
	public void update(float tpf) {
		if(currentState){
			currentState.update(tpf)
		}
		
	}
}
