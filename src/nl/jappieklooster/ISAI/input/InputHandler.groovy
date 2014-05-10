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
	String name
	List<Trigger> triggers
	List<String> names

	InputHandler(){
		this("", new ArrayList<>(), null)
	}
	InputHandler(List<Trigger> triggers, Closure handler){
		this("", triggers, handler)
	}
	InputHandler(String name, List<Trigger> triggers, Closure handler){
		this.name = name
		this.triggers = triggers
		this.handler = handler
		names = new ArrayList<>()
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
		// ignore the onaction
	}	
	
	private boolean canExecute(String name){
		return  handler != null && names.contains(name)
	}
}