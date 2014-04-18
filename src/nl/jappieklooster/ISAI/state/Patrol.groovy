package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

import com.jme3.math.Vector3f
import groovy.util.logging.*
@Log
class Patrol extends GameCharacterState{
	private boolean isGoingRight
	Patrol(GameCharacter entity){
		super(entity)
		isGoingRight = false

	}
	
	
	void enter(){
		log.info "starting to patrol"
	}

	@Override
	void update(float tpf) {
		log.info "patrolling..."
		entity.strength += tpf * GameCharacterState.damage
		
		if(entity.transform.translation.equals(new Vector3f(0,0,0))){
			
			float direction = isGoingRight ? tpf : -tpf
			entity.transform.translation.x = direction
		}
		if(!entity.enemyClose){
			return
		}
		if(entity.strength <= 10){
			stateMachine.changeState(new Hide(entity))
		}else{
			stateMachine.changeState(new Attack(entity))
		}
		
	}


	@Override
	public void exit() {
		isGoingRight = !isGoingRight
		entity.transform.translation.zero() // stop movement
		log.info "no longer patrolling"
	}
}
