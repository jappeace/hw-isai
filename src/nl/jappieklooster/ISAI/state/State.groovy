package nl.jappieklooster.ISAI.state

import nl.jappieklooster.ISAI.GameCharacter

interface State<T extends GameCharacter> {

	void Enter(T entity);
	void Execute(T entity);
	void Exit(T entity);
}
