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
import nl.jappieklooster.ISAI.behaviour.ISteerable
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
		flyCam.cam.setFrustumFar(9000)
		setUpLight()
		Random random = new Random()
		viewPort.setBackgroundColor(new ColorRGBA(0.5f, 0.3f, 0.2f, 1f));
		world = new WorldFactory(assetManager, random).make{
			name "rootnode (sortof)"

			group{

				name "standing still node"
				location new Vector3(-300, -300, -300)
				
				vehicle{
					name "wall"
					mesh new Box(1, 1080, 1920)
					location new Vector3(-20,300,300)
					texture "Textures/demon.jpg"
				}

				(0..600).each{ int number ->
					vehicle{
						name "z"
						location new Vector3(0,0,number * 2)
					}
					vehicle{
						name "y"
						location new Vector3(0,number * 2, 0)
					}
					vehicle{
						name "x"
						location new Vector3(number * 2,0, 0)
					}

				}

				(1..5).each{ int outer ->
                    (0..600).each{int number ->
                        group{
                            location new Vector3(100 * outer, 0, 100)
                            name "helix"
                            float radius = 8
                            float height = 5
                            vehicle{
                                location new Vector3(Math.sin(number)*radius, number*height, Math.cos(number)*radius)
                            }
                            vehicle{
                                location new Vector3(Math.sin(number + 180)*radius, number*height, Math.cos(number+ 180)*radius)
                            }
                        
                        }
                    }
				}
				(0..50).each{ int number ->
					vehicle{
						location new Vector3(0.01 * number, 0, 0)
					}
                    vehicle{

                        location new Vector3(number * number *3, number*2, 5*number -30)
                    }
				} }
			group{
				name "moving stuffNode"

                vehicle{
					name "explorer"
                    mesh new Sphere(5, 5, 1)
                    location new Vector3(0, -300, 0)
                    behaviour{
                        explore()
                    }
                }

				group{
					name "flocking group"


                    (0..30).each{ int number ->
                        vehicle{
                            location new Vector3(number * 8 - 20, random.nextDouble() * 10 - 20, random.nextDouble() * 10)
                            behaviour{ 
                                flock() 
                            }
                            mass 1
							friction 0
                        }
                        vehicle{
                            location new Vector3(number * 8, random.nextDouble() * 10, random.nextDouble() * 10)
                            behaviour{ 
                                flock() 
                            }
                            mass 1.5
							friction 0
                        }
                        vehicle{
                            location new Vector3(number * 8 + 20, random.nextDouble() * 10 + 20, random.nextDouble() * 10)
                            behaviour{ 
                                flock() 
                            }
                            mass 2
							friction 0
                        }
                    }
				}
				
				group{
					name "wanderers"
					location new Vector3(-500,0,0)
                    (0..40).each{ int number ->

						vehicle{
							location new Vector3(random.nextDouble() * number, random.nextDouble() *number, random.nextDouble() * number)
						}

					}
				}
				
			}
		}
        rootNode.attachChild(world.node)
	}

	@Override
	public void simpleUpdate(float tpf) {
		if(tpf > 1){ // prevent a first huge tick or any other
			return
		}
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
