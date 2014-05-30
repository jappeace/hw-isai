package nl.jappieklooster.ISAI.behaviour.state
import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.ISAI.world.entity.Vehicle
class StateMachine extends AbstractBehaviour{

	IState currentState = null
	
	/**
	 * used for identifying states with setcurrentstate "name"
	 */
	Map<String, IState> states
	StateMachine(){
		states = new HashMap<>()
	}
	void putAt(String key, IState value){
		states[key]	= value
	}
	IState getAt(String key){
		return states[key]
	}
	
	void changeState(String to){

		IState state = states[to]
		if(state == null){
			throw new IllegalArgumentException("Could not find " + to + " state. make sure you named it exactly the same")
		}
		setCurrentState(state)
	}

	void setCurrentState(IState to){
		if(currentState){
			currentState.onExit(this)
		}
		currentState = to
		if(currentState){
			currentState.onEnter(this)
		}
	}

	@Override
	void execute() {
		// TODO Auto-generated method stub
		if(currentState){
			currentState.execute(this)
			return
		}
		
	}
	Vehicle getVehicle(){
		return (Vehicle)entity
	}
}
