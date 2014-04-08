package nl.jappieklooster.ISAI

import nl.jappieklooster.ISAI.state.Patrol
import nl.jappieklooster.ISAI.state.StateMachine

class GameCharacter {
	Random r = new Random()

	int magickPower
	int strength
	StateMachine stateMachine

	GameCharacter(){
		strength = 10
		stateMachine= new StateMachine(new Patrol(this))
	}
	void update(){
		stateMachine.update();
	}
	
	boolean isEnemyClose(){
		r.nextBoolean()
	}
}
