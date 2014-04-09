package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

interface State{
	void enter()
	void update(float tpf)
	void exit()
}
