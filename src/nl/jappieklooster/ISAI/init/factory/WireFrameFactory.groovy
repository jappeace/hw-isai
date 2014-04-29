package nl.jappieklooster.ISAI.init.factory

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.debug.WireSphere

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
		Material mataterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mataterial.getAdditionalRenderState().setWireframe(true);
		mataterial.setColor("Color", new ColorRGBA(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1));
		geometry.setMaterial(mataterial);
		return geometry
	}

}
