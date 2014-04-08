package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

interface State{

	void Enter()
	void Update()
	void Exit()
}
