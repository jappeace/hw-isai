package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

import groovy.util.logging.*
@Log
class Patrol extends GameCharacterState{
	Patrol(GameCharacter entity){
		super(entity)
	}
	
	
	void enter(){
		log.info "starting to patrol"
	}

	@Override
	void update() {
		log.info "patrolling..."
		entity.strength++
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
		log.info "no longer patrolling"
	}
}
