import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*

group{
    name "moving stuffNode"

    vehicle{
        name "explorer"
        mesh new Sphere(5, 5, 1)
        location new Vector3(0, 0, 0)
        behaviour{
            explore()

        }
        write{
			text "i'm an explorer"
			scale 0.5f
            rotation 0, 0,(float)0.5*Math.PI
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
