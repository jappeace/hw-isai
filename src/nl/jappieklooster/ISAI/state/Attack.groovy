package nl.jappieklooster.ISAI.state
import nl.jappieklooster.ISAI.GameCharacter

class Attack implements State<GameCharacter>{

	@Override
	public void Enter(GameCharacter entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Execute(GameCharacter entity) {
		entity.Strength--;
		
	}

	@Override
	public void Exit(GameCharacter entity) {
		// TODO Auto-generated method stub
		
	}

}
