package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

class StateMachine<T extends GameCharacter> {

	private T entity
	State<T> currentState
	State<T> previousState
	StateMachine(T input){
		entity = input
	}
	
	void ChangeState(State<T> s){
		previousState = currentState
		currentState = s
		
	}
	
	void Update(){
		currentState?.Execute(entity)
	}
}
