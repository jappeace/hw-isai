import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*

group{
    name "moving stuffNode"

    group{
        name "flocking group"

        (0..30).each{ int number ->
            vehicle{
                location new Vector3(number * 8 - 20, random.nextDouble() * 10 - 20, random.nextDouble() * 10)
                behaviour{ 
                    flock() 
                }
                mass 0.2
                friction 0.001
            }
            vehicle{
                location new Vector3(number * 8, random.nextDouble() * 10, random.nextDouble() * 10)
                behaviour{ 
                    flock() 
                }
                mass 0.4
                friction 0.001
            }
            vehicle{
                location new Vector3(number * 8 + 20, random.nextDouble() * 10 + 20, random.nextDouble() * 10)
                behaviour{ 
                    flock() 
                }
                mass 0.6
                friction 0.001
            }
        }
    }
}
