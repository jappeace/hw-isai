package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box
import com.jme3.texture.Texture

import nl.jappieklooster.ISAI.behaviour.text.TextWriter
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
import com.jme3.math.ColorRGBA;
/**
 * specifies what a default entity should look like and also gives acces to the dsl to change those defaults
 * @author jappie
 *
 */
abstract class AEntityFactory extends AMaterialFactory{

	void setToDefault(){
		Geometry geometry = new Geometry("Default box", new Box(1,1,1) );
		geometry.setLocalTranslation(new Vector3f());

		Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		material.setTexture("DiffuseMap", assetManager.loadTexture("Textures/smile.png"));
		material.setBoolean("UseMaterialColors",false);
		material.setColor("Diffuse",ColorRGBA.Green);
		material.setColor("Specular",ColorRGBA.White);
		geometry.setMaterial(material);

		entity.geometry = geometry

		nodeContainer.node.attachChild(entity.spatial);
	}
	
	
	void mesh(Mesh shape){
		entity.mesh = shape
	} 
	
	@Override
	void location(Vector3 where){
		super.location(where)
		entity.position = where
	}
	@Override
	protected Spatial getSpatial() {
		entity.spatial
	}
	
	@Override
	protected Material getMaterial() {
		entity.material
	}
	
	TextWriter write(Closure commands){
		TextFactory factory = new TextFactory(assetManager)
		factory.setToDefault()
		new DelegateClosure(to:factory).call(commands)
		factory.result.nodeContainer = entity
		factory.result.execute() // put the text in, only callled once for entity
		return factory.result
	}

	
	protected abstract Entity getEntity()
	protected abstract AHasNode getNodeContainer()
	
}
