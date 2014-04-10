package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter
import groovy.util.logging.*
@Log
class Hide extends GameCharacterState{


	Hide(GameCharacter entity){
		super(entity)
	}
	@Override
	void enter() {
		log.info "im hiding"
		
	}

	@Override
	void update(float tpf) {
		log.info "scared and hiding ... :("
		entity.strength++
		entity.transform.rot.w += 1
		if(!entity.enemyClose){
			stateMachine.changeState(new Patrol(entity))
		}
		
	}

	@Override
	void exit() {
		entity.transform.rot.w = 0
		log.info "I'm comming out of hiding"
		
	}


}
