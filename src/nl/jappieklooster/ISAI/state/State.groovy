package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

interface State{
	void enter()
	void update()
	void exit()
}
