import nl.jappieklooster.math.vector.Vector3
import com.jme3.math.ColorRGBA
//ambient light
light{
    color = ColorRGBA.White.mult(1.5)
}
//directional 1
light {
    color = ColorRGBA.Red.mult(0.4)
    direction = new Vector3(1,-0.5,-2.8).normalized
}

//directional 2
light{
    color = ColorRGBA.Green.mult(0.4)
    direction = new Vector3(-1,-1,-2.8).normalized
}