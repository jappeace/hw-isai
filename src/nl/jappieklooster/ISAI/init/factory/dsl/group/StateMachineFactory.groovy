package nl.jappieklooster.ISAI.init.factory.dsl.group

import nl.jappieklooster.DelegateClosure;
import nl.jappieklooster.ISAI.behaviour.state.IState
import nl.jappieklooster.ISAI.behaviour.state.StateMachine
import groovy.util.logging.*
/**
 * the state machine allows users to change the state and acces the entity to attach behaviours to on runtime
 * @author jappie
 *
 */
@Log
class StateMachineFactory{
	StateMachine stateMachine
	StateMachineFactory(){
		stateMachine = new StateMachine()	
	}
	IState state(Closure arguments){
		StateFactory stateFactory = new StateFactory()

		new DelegateClosure(to:stateFactory).call(arguments)

		if(stateFactory.name == StateFactory.defaultName){
			log.warning("Creating a state with the default name, chance of overriding another state is high. This is a bad practice")	
		}

		stateMachine[stateFactory.name] = stateFactory.state

		if(stateMachine.currentState == null){
			stateMachine.currentState = stateFactory.state
		}

		if(stateFactory.active){
			stateMachine.currentState = stateFactory.state
		}

		return stateFactory.state
	}
	
}
