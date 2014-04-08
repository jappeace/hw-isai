package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

abstract class GameCharacterState implements State{

	GameCharacter entity
	GameCharacterState(GameCharacter character){
		entity = character
	}
}
