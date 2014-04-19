package nl.jappieklooster.ISAI

import com.jme3.app.SimpleApplication
import com.jme3.light.DirectionalLight
import com.jme3.material.Material
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.scene.shape.Sphere
import com.jme3.math.ColorRGBA
import com.jme3.math.Transform
import com.jme3.math.Vector3f
import nl.jappieklooster.ISAI.factory.WorldFactory
import nl.jappieklooster.math.vector.Vector3

class Window extends SimpleApplication {

	World world

	Window(){

		
	}
	@Override
	public void simpleInitApp() {
		world = new WorldFactory(assetManager).make{
			vehicle{
				mesh new Sphere(10, 10, 5)
				location new Vector3(-3, 1.1, 0)
			}
			
			vehicle{
				location new Vector3(5,5,5)
			}
		}
		rootNode.attachChild(world.node)
	}
	
	@Override
    public void simpleUpdate(float tpf) {
		world.update(tpf);
    }
}
