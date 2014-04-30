package nl.jappieklooster.ISAI.init.factory.dsl.env

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.scene.Spatial
import com.jme3.scene.Node

import nl.jappieklooster.ISAI.init.factory.dsl.ASpatialFactory;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

/** an instance of thiss class has a spatial and material for that spatial, meaning it can edit it */
class SkyFactory extends ASpatialFactory{
	World world
	AssetManager assetManager
	private Node node
	boolean isSphere
	String texture
	SkyFactory(World world){
		super(null)
		node = new Node("sky-contiainer")
		texture = "Textures/Sky/Bright/BrightSky.dds"
		isSphere = false
		this.world = world
		world.node.attachChild(node)
	}
	
	Spatial create(){
		Spatial result = com.jme3.util.SkyFactory.createSky(assetManager, texture, isSphere)
		node.attachChild(result)
		return result
	}
	
	@Override
	public Spatial getSpatial() {
		return node
	}
}
