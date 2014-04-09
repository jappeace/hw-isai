package nl.jappieklooster.ISAI

import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.math.ColorRGBA
import com.jme3.math.Transform
import com.jme3.math.Vector3f

class Window extends SimpleApplication {

	World world

	Window(){
		
	}
	@Override
	public void simpleInitApp() {
		world = new World()
		world.zelda = new GameCharacter()
		Transform transform = new Transform()
		world.zelda.transform = transform
		createCube().setLocalTransform(transform)
	}
	
	@Override
    public void simpleUpdate(float tpf) {
		world.zelda.update(tpf)
		rootNode.children.each{
			it.setTransformRefresh()
		}
    }
	
	private Geometry createCube(){
        Geometry geom = new Geometry("Box", new Box(1, 1, 1));  // create cube geometry from the shape
        Material mat = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
        geom.setMaterial(mat);                   // set the cube's material
        rootNode.attachChild(geom);
	}
}
