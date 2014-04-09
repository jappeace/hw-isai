package nl.jappieklooster.ISAI

import com.jme3.math.Transform
import nl.jappieklooster.ISAI.state.Patrol
import nl.jappieklooster.ISAI.state.StateMachine

class GameCharacter {
	Random r = new Random()

	int magickPower
	int strength
	Transform transform
	StateMachine stateMachine

	GameCharacter(){
		strength = 10
		stateMachine= new StateMachine(new Patrol(this))
	}
	void update(float tpf){
		stateMachine.update(tpf);
	}
	
	boolean isEnemyClose(){
		r.nextBoolean()
	}
}
