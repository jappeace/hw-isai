package nl.jappieklooster.ISAI.init.factory.dsl.env

import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.light.Light
import com.jme3.math.ColorRGBA
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

/**
 * creates the lights
 * @author jappie
 *
 */
class LightFactory {

	Light light
	Vector3 direction = null
	ColorRGBA color

	LightFactory(){
		light = new AmbientLight()
		color = new ColorRGBA(1f,1f,1f,0.5f)
	}
	void setDirection(Vector3 direction){
		this.direction = direction.normalized
		updateLight()
	}
	void setColor(ColorRGBA to){
		this.color = to
		updateLight()
	}
	void red(double value){
		color.r = (float)value
		updateLight()
	}
	void green(double value){
		color.g = (float) value
		updateLight()
	}
	void blue(double value){
		color.b = (float)value
		updateLight()
	}
	void alfa(double value){
		color.a = (float) value
		updateLight()
	}
	private void updateLight(){
		if(direction){
			DirectionalLight dirLight = new DirectionalLight()
			dirLight.direction = Converter.toJME(direction)
			dirLight.color = color
			light = dirLight
			return
		}
		light = new AmbientLight()
		light.color = color
	}
}
