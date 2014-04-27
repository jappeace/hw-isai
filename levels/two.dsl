import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*
make{

    name "rootnode (sortof)"
    group{

        name "standing still node"
        location new Vector3(-300, -300, -300)
        
        vehicle{
            name "wall"
            mesh new Box(1, 1080, 1920)
            location new Vector3(20,-300,150)
            texture "Textures/demon.jpg"
        }

        (1..2).each{ int outer ->
            (0..600).each{int number ->
                group{
                    location new Vector3(100 * outer, 0, 100)
                    name "helix"
                    float radius = 8
                    float height = 5
                    vehicle{
                        location new Vector3(Math.sin(number/Math.PI)*radius, number*height, Math.cos(number/Math.PI)*radius)
                    }
                    vehicle{
                        location new Vector3(Math.sin(number/Math.PI + Math.PI)*radius, number*height, Math.cos(number/Math.PI+ Math.PI)*radius)
                    }
                
                }
            }
        }
    }
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
            name "wanderers"
            (0..90).each{ int number ->

                vehicle{
                    location new Vector3(random.nextDouble() * number, random.nextDouble() *number, random.nextDouble() * number)
                    mass 1
                    behaviour{
                        wander()
                    }
                }

            }
        }
        
    }

}