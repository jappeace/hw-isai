package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture

import nl.jappieklooster.ISAI.init.DelegateClosure;
import nl.jappieklooster.ISAI.init.factory.dsl.AMaterialFactory;
import nl.jappieklooster.ISAI.world.AHasNode
import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity
import nl.jappieklooster.ISAI.world.entity.Vehicle;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

/**
 * specifies what a default entity should look like and also gives acces to the dsl to change those defaults
 * @author jappie
 *
 */
abstract class AEntityFactory extends AMaterialFactory{

	void setToDefault(){
		Geometry geometry = new Geometry("Default box", new Box(1,1,1) );
		geometry.setLocalTranslation(new Vector3f());

		Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		material.setTexture("ColorMap", assetManager.loadTexture("Textures/smile.png"));

		geometry.setMaterial(material);

		entity.geometry = geometry

		nodeContainer.node.attachChild(geometry);
	}
	
	
	void mesh(Mesh shape){
		entity.geometry.mesh = shape
	} 
	
	@Override
	void location(Vector3 where){
		super.location(where)
		entity.position = where
	}
	@Override
	protected Spatial getSpatial() {
		return entity.geometry
	}
	
	@Override
	protected Material getMaterial() {
		entity.geometry.material
	}
	
	protected abstract Entity getEntity()
	protected abstract AHasNode getNodeContainer()
	
}
