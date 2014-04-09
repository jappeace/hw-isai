package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

class StateMachine{

	private State currentState
	private State previousState
	StateMachine(State startingState){
		currentState = startingState
		previousState = null
	}
	
	void changeState(State state){
		state = state ?: currentState

		previousState = currentState
		currentState = state
		
		previousState.exit()
		currentState.enter()
	}
	
	void update(float tpf){
		currentState?.update(tpf)
	}
}
