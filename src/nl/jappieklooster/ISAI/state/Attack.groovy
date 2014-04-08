package nl.jappieklooster.ISAI.state
import nl.jappieklooster.ISAI.GameCharacter

import groovy.util.logging.*
@Log
class Attack extends GameCharacterState{

	Attack(GameCharacter entity){
		super(entity)
	}
	@Override
	public void enter() {
		// TODO Auto-generated method stub
		log.info "CHAAAARRGGGGEEEHHHHH!!!"
		
	}
	@Override
	void update() {
		log.info "The battle is going glorieus"
		entity.strength--
		if(entity.strength < 5){
			stateMachine.changeState(new Hide(entity))
		}
		
	}
	@Override
	public void exit() {
		log.info "Retreaat!!!"
	}
}
