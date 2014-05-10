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
	private List<InputHandler> handlers
	InputDirector(InputManager manager){
		inputManager = manager
		resetHandlers()
	}
	private void resetHandlers(){
		handlers = new LinkedList<>()
	}

	void addHandler(InputHandler handler){
		handlers.add(handler)
		
		// fix naming and attach mappings
		handler.triggers.eachWithIndex { Trigger trigger, int index ->
			if(index <= handler.names.size()){
				handler.names[index] = handler.handlerName + trigger.name
			}
			inputManager.addMapping(handler.names[index], trigger)
		}

		String[] names = (String[])handler.names.toArray()
		inputManager.addListener(handler, names)
	}
	void removeHandler(InputHandler handler){
		inputManager.removeListener(handler)
	}
	
	void removeMyHandlers(){
		handlers.each{
			inputManager.removeListener(it)
		}
		resetHandlers()
	}

}
