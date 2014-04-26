package nl.jappieklooster.ISAI.init.factory

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture

import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.entity.Vehicle;
import nl.jappieklooster.ISAI.entity.tracking.NeighbourTracker
import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

class VehicleFactory extends SpatialFactory{
	Vehicle vehicle
	private AssetManager assetManager

	VehicleFactory(World world, AssetManager manager, NeighbourTracker tracker, Random random){
		super(world, tracker, random)

		vehicle = new Vehicle()
		vehicle.random = random
		assetManager = manager
		Geometry geometry = new Geometry("Default box", new Box(1,1,1) );
		geometry.setLocalTranslation(new Vector3f());

		Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

		material.setTexture("ColorMap", assetManager.loadTexture("Textures/smile.png"));

		geometry.setMaterial(material);
		world.node.attachChild(geometry);
		vehicle.geometry = geometry
		vehicle.velocity = new Vector3()
		vehicle.position = new Vector3()
	}
	
	void texture(String path){
		vehicle.geometry.material.setTexture("ColorMap", assetManager.loadTexture(path))
	}
	
	void mesh(Mesh shape){
		vehicle.geometry.mesh = shape
	} 
	
	@Override
	void location(Vector3 where){
		super.location(where)
		vehicle.position = where
	}
	void behaviour(Closure commands){
		world.shouldUpdate = true
		new DelegateClosure(to:new BehaviourFactory(vehicle, world, neighTracker, random)).call(commands)
	}

	@Override
	public Spatial getSpatial() {
		return vehicle.geometry
	}
	
	void friction(float to){
		vehicle.friction = to
	}
	
	void mass(float mass){
		vehicle.mass = mass
	}
	
	void heading(Vector3 to){
		vehicle.heading = to.normalized
	}
	
}
