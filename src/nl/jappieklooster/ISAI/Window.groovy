package nl.jappieklooster.ISAI

import com.jme3.app.SimpleApplication
import com.jme3.light.DirectionalLight
import com.jme3.material.Material
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.math.ColorRGBA
import com.jme3.math.Transform
import com.jme3.math.Vector3f

class Window extends SimpleApplication {

	World world

	Window(){
		
	}
	@Override
	public void simpleInitApp() {
		world = new World()
	}
	
	@Override
    public void simpleUpdate(float tpf) {
		world.update(tpf);
    }
}
