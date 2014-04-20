package nl.jappieklooster.ISAI

import com.jme3.app.SimpleApplication
import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.material.Material
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.scene.shape.Dome
import com.jme3.scene.shape.Sphere
import com.jme3.scene.shape.StripBox
import com.jme3.math.ColorRGBA
import com.jme3.math.Transform
import com.jme3.math.Vector3f
import nl.jappieklooster.ISAI.factory.WorldFactory
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class Window extends SimpleApplication {

	World world

	Window(){
	}
	@Override
	public void simpleInitApp() {
		flyCam.setMoveSpeed(100)
		setUpLight()
		viewPort.setBackgroundColor(new ColorRGBA(0.5f, 0.3f, 0.2f, 1f));
		world = new WorldFactory(assetManager).make{
			vehicle{
				mesh new Sphere(10, 100, 5)
				location new Vector3(-3, 1.1, 0)
			}

			group{
				(0..20).each{ int number ->
					vehicle{
						location new Vector3(0,0,-20 * number)
					}
				}
			}
			group{
				(0..10).each{ int number ->
					vehicle{
						location new Vector3(5*number,-7 * number, 6 * number - 50)
						behaviour{ 
							flock() 
                        }
						speed 10
					}
					vehicle{
						location new Vector3(8*number,-10 * number, 7 * number - 30)
						behaviour{ 
							flock() 
                        }
						speed 12
					}
				}
			}
			group{
				(0..20).each{ int number ->
					vehicle{
						location new Vector3(0.01 * number, 0, 0)
					}
				}
			}
			rootNode.attachChild(world.node)
		}
	}

	@Override
	public void simpleUpdate(float tpf) {
		world.update(tpf);
	}
	private void setUpLight() {
		// We add light so we see the scene
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(al);

		DirectionalLight dl = new DirectionalLight();
		dl.setColor(ColorRGBA.White);
		dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
		rootNode.addLight(dl);
	}
}
