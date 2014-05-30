package nl.jappieklooster.ISAI.init.factory.dsl.group

import nl.jappieklooster.ISAI.behaviour.state.IEnterable
import nl.jappieklooster.ISAI.behaviour.state.IExecutable
import nl.jappieklooster.ISAI.behaviour.state.IExitable
import nl.jappieklooster.ISAI.behaviour.state.IState
import nl.jappieklooster.ISAI.behaviour.state.State

/**
 * allows users to assing states to their vehicles
 * @author jappie
 *
 */
class StateFactory {
	/**
	 * the acttual state
	 */
	State state
	
	/**
	 * the state name is used to switch between states
	 */
	String name
	
	/**
	 * only one state can be active at a time, use the active var to tell which state goes first
	 * the last state marked active will become active. this is default false. 
	 * If no state is marked active the first state will be marked active
	 */
	boolean active = false
	static final defaultName = "std"
	StateFactory(){
		state = new State()
		name = defaultName
	}
	
	IState enter(IEnterable onEnter){
		state.enterCallback = onEnter
	}
	IState exit(IExitable onExit){
		state.exitCallback = onExit
	}
	IState execute(IExecutable onExecute){
		state.executer = onExecute
	}
	
		
}
