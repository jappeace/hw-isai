package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

class StateMachine{

	State currentState
	State previousState
	
	void ChangeState(State s){
		previousState = currentState
		currentState = s
		
	}
	
	void Update(){
		currentState?.Update()
	}
}
