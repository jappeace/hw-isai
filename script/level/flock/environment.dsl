import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*

environment{

    name "rootnode (sortof)"

    sky{

    }
    group{
        name "standing still node"
        location new Vector3(-300, -300, -300)
        
        obstacle{
            name "wall"
            mesh new Box(1, 1080, 1920)
            location new Vector3(-20,300,300)
            texture "Textures/demon.jpg"
        }

        (0..600).each{ int number ->
            obstacle{
                name "z"
                location new Vector3(0,0,number * 2)
            }
            obstacle{
                name "y"
                location new Vector3(0,number * 2, 0)
            }
            obstacle{
                name "x"
                location new Vector3(number * 2,0, 0)
            }

        }

        (1..2).each{ int outer ->
            (0..600).each{int number ->
                group{
                    location new Vector3(100 * outer, 0, 100)
                    name "helix"
                    float radius = 8
                    float height = 5
                    obstacle{
                        location new Vector3(Math.sin(number/Math.PI)*radius, number*height, Math.cos(number/Math.PI)*radius)
                    }
                    obstacle{
                        location new Vector3(Math.sin(number/Math.PI + Math.PI)*radius, number*height, Math.cos(number/Math.PI+ Math.PI)*radius)
                    }
                
                }
            }
        }
        (0..50).each{ int number ->
            obstacle{
                location new Vector3(0.01 * number, 0, 0)
            }
            obstacle{

                location new Vector3(number * number *3, number*2, 5*number -30)
            }
        }
    }
}
