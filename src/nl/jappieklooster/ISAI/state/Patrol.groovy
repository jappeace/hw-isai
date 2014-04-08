package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

class Patrol extends GameCharacterState{
	Patrol(GameCharacter entity){
		super(entity)
	}

	@Override
	public void Enter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		entity.strength++
		
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}

}
