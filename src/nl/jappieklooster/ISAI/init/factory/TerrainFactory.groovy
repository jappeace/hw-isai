package nl.jappieklooster.ISAI.init.factory

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.scene.Node
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainLodControl
import com.jme3.terrain.geomipmap.TerrainQuad
import com.jme3.terrain.heightmap.AbstractHeightMap
import com.jme3.terrain.heightmap.ImageBasedHeightMap
import com.jme3.texture.Texture
import com.jme3.texture.Texture.WrapMode

class TerrainFactory extends AMaterialFactory{

	/**
	 * allows changing of the terrain material
	 */
	private Material material
	
	/**
	 * terrain does not allow editing of the height map
	 * because of this the terrain needs to be created at the last moment
	 * to allow spatial mutations on terrain this node is used and the terrain is later
	 * attached to it
	 */
	private Node spatialContainer
	
	float[] heightMap

    int patchSize = 65;
	TerrainFactory(){
		spatialContainer = new Node("terrain container")

        /** 1. Create terrain material and load four textures into it. */
        material = new Material(assetManager, 
                "Common/MatDefs/Terrain/Terrain.j3md");
     	heightMap = new float[0]
	}
	
	void setToDefault(){
        /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
        material.setTexture("Alpha", assetManager.loadTexture(
                "Textures/Terrain/splat/alphamap.png"));
     
        /** 1.2) Add GRASS texture into the red layer (Tex1). */
        Texture grass = assetManager.loadTexture(
                "Textures/Terrain/splat/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        material.setTexture("Tex1", grass);
        material.setFloat("Tex1Scale", 64f);
     
        /** 1.3) Add DIRT texture into the green layer (Tex2) */
        Texture dirt = assetManager.loadTexture(
                "Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(WrapMode.Repeat);
        material.setTexture("Tex2", dirt);
        material.setFloat("Tex2Scale", 32f);
     
        /** 1.4) Add ROAD texture into the blue layer (Tex3) */
        Texture rock = assetManager.loadTexture(
                "Textures/Terrain/splat/road.jpg");
        rock.setWrap(WrapMode.Repeat);
        material.setTexture("Tex3", rock);
        material.setFloat("Tex3Scale", 128f);


		
		// if threre is a world attach the spatialcontainer to it
		world?.node.attachChild(spatialContainer)
	}
	
	float[] loadHeightMap(String path){
        /** 2. Create the height map */
        AbstractHeightMap heightmap = null;
        Texture heightMapImage = assetManager.loadTexture(path);
        heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();
		heightMap = heightmap.getHeightMap()
	}
	TerrainQuad create(){
		
		// if no user defined heightmap was found do the defualt (quite heavy call)
		if(heightMap == new float[0]){
			loadHeightMap("Textures/Terrain/splat/mountains512.png")
		}
        /** 3. We have prepared material and heightmap. 
         * Now we create the actual terrain:
         * 3.1) Create a TerrainQuad and name it "my terrain".
         * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
         * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
         * 3.4) As LOD step scale we supply Vector3f(1,1,1).
         * 3.5) We supply the prepared heightmap itself.
         */
		TerrainQuad terrain = new TerrainQuad("terrain", patchSize, Math.sqrt(heightMap.length) + 1, heightMap);
		
        /** 4. We give the terrain its material, position & scale it, and attach it. */
        terrain.setMaterial(material);
     
        /** 5. The LOD (level of detail) depends on were the camera is: */
        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
		spatialContainer.attachChild(terrain)
		return terrain
	}
	
	@Override
	Spatial getSpatial() {
		return spatialContainer
	}

	@Override
	public Material getMaterial() {
		return material
	}

}
