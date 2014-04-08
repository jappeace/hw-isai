package nl.jappieklooster.ISAI

import nl.jappieklooster.ISAI.state.StateMachine

class GameCharacter {

	int magickPower
	int strength
	StateMachine machine

	GameCharacter(){
		machine = new StateMachine(this)
	}
	void Update(){
		machine.Update();
	}
	
	boolean isInRange(){
		
	}
}
