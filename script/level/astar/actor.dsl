import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*

group{
    vehicle{
    	clickable()
    	scale new Vector3(2.5)
    	mass 0.1
    }
    vehicle{
    	location new Vector3(20,0,0)
        mesh new Sphere(10, 10, 1)	
    }
}
