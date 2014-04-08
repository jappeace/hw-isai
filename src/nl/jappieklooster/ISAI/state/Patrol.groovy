package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

class Patrol implements State<GameCharacter> {

	@Override
	public void Enter(GameCharacter entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Execute(GameCharacter entity) {
		entity.Strength++
		
	}

	@Override
	public void Exit(GameCharacter entity) {
		// TODO Auto-generated method stub
		
	}

}
