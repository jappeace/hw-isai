package nl.jappieklooster.ISAI.input

import com.jme3.input.InputManager
import com.jme3.input.controls.Trigger

class InputDirector {
	private InputManager inputManager
	InputDirector(InputManager manager){
		inputManager = manager
	}

	void addHandler(InputHandler handler){
		handler.triggers.eachWithIndex { Trigger trigger, int index ->
			String mappingName =  index < handler.names.size()? handler.names[index] : trigger.name
			inputManager.addMapping(mappingName, trigger)
		}
		inputManager.addListener(handler, (String[])handler.names.toArray())
	}
	void removeHandler(InputHandler handler){
		inputManager.removeListener(handler)
	}

}
