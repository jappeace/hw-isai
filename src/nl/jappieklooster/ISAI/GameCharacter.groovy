package nl.jappieklooster.ISAI

import nl.jappieklooster.ISAI.state.StateMachine

class GameCharacter {

	int MagickPower
	int Strength
	
	StateMachine<GameCharacter> machine
	GameCharacter(){
		machine = new StateMachine<>(this)
	}
	void Update(){
		machine.Update();
	}
}
