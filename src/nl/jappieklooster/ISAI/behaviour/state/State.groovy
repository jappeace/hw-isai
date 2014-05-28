package nl.jappieklooster.ISAI.behaviour.state

/**
 * this class is used by the dsl to put the closures in provided by the user
 * @author jappie
 *
 */
class State implements IState{

	IEnterable enterCallback
	IExitable exitCallback
	IExecutable executer
	State(){
		enterCallback = {}
		exitCallback = {}
		executer = {}
	}
	@Override
	public void onEnter(StateMachine stateMachine) {
		enterCallback.onEnter(stateMachine)
	}

	@Override
	public void onExit(StateMachine stateMachine) {
		exitCallback.onExit(stateMachine)
	}

	@Override
	public void execute(StateMachine stateMachine) {
		executer.execute(stateMachine)
	}

}
