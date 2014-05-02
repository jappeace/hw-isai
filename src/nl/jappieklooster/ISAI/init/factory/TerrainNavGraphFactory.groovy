package nl.jappieklooster.ISAI.init.factory

import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.terrain.geomipmap.TerrainQuad
import nl.jappieklooster.ISAI.world.entity.graph.Graph
import nl.jappieklooster.ISAI.world.entity.graph.Vertex
import nl.jappieklooster.math.vector.Vector3

class TerrainNavGraphFactory {

	/**
	 * will be automaticly created, but can be overwritten
	 */
	Graph graph
	Random random
	
	/**
	 * is not magicly created and has to be set
	 */
	TerrainQuad terrain
	
	TerrainNavGraphFactory(){
		random = new Random()
		graph = new Graph()
		graph.name += " navgraph for terrain"
	}
	
	/**
	 * this method goes trough the terrain on the x and z axis and will request the height, then it will add a vetex for that position to the graph
	 * 
	 * @param cellSize, this is used to determin the next x or z coordinate
	 * @param edgeDistance
	 */
	void addRasterVerteciToGraph(float cellSize){
		Vector3f scale = terrain.getWorldScale()
		Vector3f position = terrain.worldTranslation

		float width = (terrain.terrainSize * scale.x )/2 
		float depth = (terrain.terrainSize * scale.z )/2
		
		float xinit = -width + position.x
		float x = xinit
		float z = -depth + position.z

		while(true){
			x += cellSize * scale.x

			if(x >= width + position.x){
				x = xinit
				z += cellSize * scale.z
			}

			if(z >= depth  + position.z){
				break;
			}

			float height = terrain.getHeight( new Vector2f(x, z))

			if(height == Float.NaN){
				// its very nice of jme to not crash to program when it does not find a height
				// but when it does not find a height, I have a bug
				throw new Exception("height not found");
			}

			graph.add(
				new Vertex(
					new Vector3(
						x + random.nextFloat(), 
						height + position.y, 
                        z
                    )
                )
            )
			
		}
	}
	void connectVerticiCloserThen(float edgeDistance){
		
	}
}
