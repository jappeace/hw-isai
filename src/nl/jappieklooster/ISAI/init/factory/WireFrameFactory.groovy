package nl.jappieklooster.ISAI.init.factory

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.debug.WireBox
import com.jme3.scene.debug.WireSphere
import nl.jappieklooster.math.vector.Vector3

/**
 * A singleton factory that allows the (easy) creation of wireframe debug shapes
 * troughout the program without having to pass around the asset manager to evereything
 * @author jappie
 *
 */
class WireFrameFactory {
	private static WireFrameFactory instance = new WireFrameFactory()
	private WireFrameFactory(){

	}
	static WireFrameFactory getInstance(){
		return instance
	}

	private AssetManager assetManager

	// dont care about exposing random because... well its random, more acces to random means more random, and we want random te be random, don't we?
	Random random

	void setAssetManager(AssetManager to){
		assetManager = to
	}


	Geometry createSphere(int size = 1){
		Geometry geometry = new Geometry("wireframe sphere", new WireSphere(size));
		Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		material.getAdditionalRenderState().setWireframe(true);
		material.setColor("Color", createRandomColor());
		geometry.setMaterial(material);
		return geometry
	}
	
	Geometry createCube(Vector3 size = new Vector3(1)){
		  Geometry geometry = new Geometry("wireframe cube", new WireBox(size.x, size.y, size.z));
          Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
          material.getAdditionalRenderState().setWireframe(true);
          material.setColor("Color", createRandomColor());
          geometry.setMaterial(material);
          return geometry;
	}
	
	private ColorRGBA createRandomColor(){
		new ColorRGBA(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1)
	}
}
