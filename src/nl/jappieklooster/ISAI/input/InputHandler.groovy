package nl.jappieklooster.ISAI.input

import com.jme3.input.controls.AnalogListener
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.Trigger

/**
 * delegates a input to an closure, it helps with structuring the input for the input manager
 * @author jappie
 *
 */
class InputHandler implements ActionListener, AnalogListener{
	/**
	 * @Prototype void(float,float,String)
	 */
	Closure handler
	
	/**
	 * allow multiple handlers to be attached to the same key (optional
	 */
	String handlerName
	List<Trigger> triggers
	List<String> names

	InputHandler(){
		this(new ArrayList<>(), null)
		names = new ArrayList<>()
		handlerName = ""
	}
	InputHandler(List<Trigger> triggers, Closure handler){
		this.triggers = triggers
		this.handler = handler
	}
	@Override
	public void onAnalog(String name, float value, float tpf) {
		if(!canExecute(name)){
			return
		}
		handler(value, tpf, name)
		
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if(!canExecute(name)){
			return
		}
		handler(isPressed ? 1 : 0, tpf, name) // i mean a boolean fits like 32 times in a float sortof
	}	
	
	private boolean canExecute(String name){
		return  handler != null && names.contains(name)
	}
}