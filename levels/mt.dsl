import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*

name "rootnode (sortof)"

environment{
    sky{
    }
    terrain{
        scale new Vector3(5, 1, 4)
        location new Vector3(0, 0, 0)
    }
    obstacle{
        name "wall"
        mesh new Box(1, 1080, 1920)
        location new Vector3(-20,300,300)
        texture "Textures/demon.jpg"
    }
}
group{

    name "standing still node"
    location new Vector3(-300, -300, -300)
    

    (0..1).each{ int outer ->
        (0..900).each{int number ->
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
}
