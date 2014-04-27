package nl.jappieklooster.ISAI.input

import com.jme3.input.controls.AnalogListener
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.Trigger

class InputHandler implements ActionListener, AnalogListener{
	/**
	 * @Prototype void(float,float,String)
	 */
	Closure handler
	
	List<Trigger> triggers
	List<String> names

	InputHandler(){
		triggers = new ArrayList<>()
		names = new ArrayList<>()
	}
	@Override
	public void onAnalog(String name, float value, float tpf) {
		handler(value, tpf, name)
		
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		handler(isPressed ? 1 : 0, tpf, name) // i mean a boolean fits like 32 times in a float sortof
	}	
}