package nl.jappieklooster.ISAI.init.factory

import com.jme3.math.Vector2f
import com.jme3.terrain.geomipmap.TerrainQuad
import nl.jappieklooster.ISAI.world.entity.graph.Graph
import nl.jappieklooster.ISAI.world.entity.graph.Vertex
import nl.jappieklooster.math.vector.Vector3

class TerrainNavGraphFactory {

	/**
	 * will be automaticly created, but can be overwritten
	 */
	Graph graph
	
	/**
	 * is not magicly created and has to be set
	 */
	TerrainQuad terrain
	
	TerrainNavGraphFactory(){
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
		float width = terrain.terrainSize * terrain.localScale.x
		float loopcount =  width * terrain.terrainSize * terrain.localScale.z
		float x = 0, z = 0
		for(float i = 0; i < loopcount; i += cellSize){
			x += cellSize * terrain.localScale.x
			if(x >= width){
				x = 0
				z += cellSize * terrain.localScale.z
			}
			graph.add(
				new Vertex(
					new Vector3(
						x, 
						terrain.getHeight(
							new Vector2f(x, z)
						), 
                        z
                    )
                )
            )
			
		}
	}
	void connectVerticiCloserThen(float edgeDistance){
		
	}
}
