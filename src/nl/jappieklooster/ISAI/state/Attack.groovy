package nl.jappieklooster.ISAI.state
import nl.jappieklooster.ISAI.GameCharacter

class Attack extends GameCharacterState{

	Attack(GameCharacter entity){
		super(entity)
	}
	@Override
	void Enter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void Update() {
		// TODO Auto-generated method stub
		entity.Strength--
		
	}

	@Override
	void Exit() {
		// TODO Auto-generated method stub
		
	}


}
