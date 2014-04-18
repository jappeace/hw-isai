package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter
abstract class GameCharacterState implements State{

	final static float damage = 5
	GameCharacter entity
	GameCharacterState(GameCharacter character){
		entity = character
	}
	
	StateMachine getStateMachine(){
		return entity.stateMachine
	}
}
