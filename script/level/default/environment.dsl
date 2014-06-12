import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*

environment{
    obstacle{
        name "wall"
        mesh new Box(1, 1080, 1920)
        location new Vector3(20,-300,150)
        texture "Textures/demon.jpg"
    }
    location new Vector3(-200,-100,300)
    sky{
    }
    terrain{
        scale new Vector3(5, 1, 4)
        location new Vector3(0, 0, 0)
    }
}