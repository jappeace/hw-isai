import nl.jappieklooster.math.vector.Vector3
import com.jme3.scene.shape.*

name "rootnode (sortof)"

environment{
	location new Vector3(-200,-500,300)
    sky{
    }

	obstacle{
		scale new Vector3(600,300,1)
		location new Vector3(0,0,-800)
	}
    group{
        name "helix"
        (0..900).each{int number ->
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
    terrain{
        scale new Vector3(5, 1, 4)
        location new Vector3(0, 0, 0)
    }
}

group{
    unit{
    }
}
