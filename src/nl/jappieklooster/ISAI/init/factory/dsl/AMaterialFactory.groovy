package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.scene.Spatial

import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

/** an instance of thiss class has a spatial and material for that spatial, meaning it can edit it */
abstract class AMaterialFactory extends ASpatialFactory{

	AssetManager assetManager

	AMaterialFactory(){
		super(null)
	}
	AMaterialFactory(NeighbourTracker tracker){
		super(tracker)
	}
	abstract Material getMaterial()

	void texture(String path){
		getMaterial().setTexture("ColorMap", assetManager.loadTexture(path))
	}
	
	void texture(String name, String path){
		getMaterial().setTexture(name, assetManager.loadTexture(path))
	}
	
	void value(String name, float value){
		getMaterial().setFloat(name, value)
	}
}
