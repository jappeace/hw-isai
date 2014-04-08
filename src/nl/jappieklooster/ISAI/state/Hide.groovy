package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter
class Hide extends GameCharacterState{


	Hide(GameCharacter entity){
		super(entity)
	}
	@Override
	void Enter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void Update() {
		// TODO Auto-generated method stub
		entity.strength++
		
	}

	@Override
	void Exit() {
		// TODO Auto-generated method stub
		
	}


}
