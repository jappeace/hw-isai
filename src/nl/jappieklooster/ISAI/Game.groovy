package nl.jappieklooster.ISAI

import com.jme3.app.SimpleApplication
import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import nl.jappieklooster.ISAI.init.LevelLoader


class Game extends SimpleApplication {

	World world
	LevelLoader loader
	Game(){
	}
	@Override
	public void simpleInitApp() {
		loader = new LevelLoader(assetManager)
		flyCam.setMoveSpeed(100)
		flyCam.cam.setFrustumFar(9000)
		setUpLight()
		Random random = new Random()
		viewPort.setBackgroundColor(new ColorRGBA(0.5f, 0.3f, 0.2f, 1f));

		world = loader.loadFile("levels/one")
        rootNode.attachChild(world.node)
	}

	@Override
	public void simpleUpdate(float tpf) {
		if(tpf > 1){ // prevent a first huge tick or any other
			return
		}
		world.update(tpf);
	}
	private void setUpLight() {
		// We add light so we see the scene
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(al);

		DirectionalLight dl = new DirectionalLight();
		dl.setColor(ColorRGBA.White);
		dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
		rootNode.addLight(dl);
	} 
	@Override
	void destroy() {
        super.destroy();
		loader.releaseThreadPool()
        //executor.shutdown();
    }
}
