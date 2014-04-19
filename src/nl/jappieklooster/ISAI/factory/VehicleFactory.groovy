package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.entity.impl.Vehicle
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class VehicleFactory extends AbstractFactory{
	Vehicle vehicle
	VehicleFactory(World world, AssetManager manager){

		vehicle = new Vehicle()
		Geometry geometry = new Geometry("Default box", new Box(1,1,1));
		geometry.setLocalTranslation(new Vector3f());

		Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

		material.setTexture("ColorMap", assetManager.loadTexture("Interface/Logo/Monkey.jpg"));

		geometry.setMaterial(material);
		world.node.attachChild(geometry);
		vehicle.geometry = geometry
	}
	
	void texture(String path){
		vehicle.geometry.material.setTexture("ColorMap", assetManager.loadTexture(path))
	}
	
	void mesh(Mesh shape){
		vehicle.geometry.mesh = shape
	} 
	
	void location(Vector3 where){
		vehicle.geometry.setLocalTranslation(Converter.toJME(where))	
	}
	void rotation(Vector3 how){
		vehicle.geometry.rotateUpTo(Converter.toJME(how))
	}
	
}
