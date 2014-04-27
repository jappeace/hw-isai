package nl.jappieklooster.ISAI.input

import com.jme3.input.InputManager
import com.jme3.input.controls.Trigger

/**
 * this input directer wraps arround a inputManager
 * it makes naming completly optional
 * just pass a new Inputhandler to it with a closure that handles the code
 * @author jappie
 *
 */
class InputDirector {
	private InputManager inputManager
	InputDirector(InputManager manager){
		inputManager = manager
	}

	void addHandler(InputHandler handler){
		handler.triggers.eachWithIndex { Trigger trigger, int index ->
			if(index < handler.names.size()){
				handler.names[index] = trigger.name
			}
			inputManager.addMapping(handler.names[index], trigger)
		}
		inputManager.addListener(handler, (String[])handler.names.toArray())
	}
	void removeHandler(InputHandler handler){
		inputManager.removeListener(handler)
	}

}
