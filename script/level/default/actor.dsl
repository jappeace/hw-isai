import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*

group{
    name "moving stuffNode"

    group{
        name "wanderers"
        (0..90).each{ int number ->

            vehicle{
                location new Vector3(random.nextDouble() * number, random.nextDouble() *number, random.nextDouble() * number)
                mass 1
                behaviour{
                    wander()
                }
                write{
                    text {"There are no characters :*( \n these are default"}
                    scale 0.3f
                    location 2f, 5f, 0f
                }
            }

        }
    }
    
}
