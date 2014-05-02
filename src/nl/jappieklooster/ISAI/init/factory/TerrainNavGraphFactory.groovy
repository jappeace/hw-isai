package nl.jappieklooster.ISAI.init.factory

import com.jme3.collision.CollisionResult
import com.jme3.collision.CollisionResults
import com.jme3.math.Ray
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.terrain.geomipmap.TerrainQuad
import com.jme3.scene.Node

import nl.jappieklooster.ISAI.collection.graph.Graph;
import nl.jappieklooster.ISAI.collection.graph.Vertex;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

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
	
	/**
	 * this node is checked aggain to see if a connection can be made in the graph
	 * when the terrain is set it is automaticly attached to this node
	 */
	Node collidable
	
	TerrainNavGraphFactory(){
		random = new Random()
		graph = new Graph()
		graph.name += " navgraph for terrain"
		
		collidable = new Node("collision node for navgraph")
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
						height + position.y + 3f, 
                        z
                    )
                )
            )
			
		}
	}
	void connectVerticiCloserThen(float edgeDistance){
		
		graph.verteci.each{ Vertex outer ->
			graph.verteci.each{ Vertex inner ->

				if(inner.position == outer.position){
					return
				}

				Vector3 difference = outer.position - inner.position

				if(difference.lengthSq >= edgeDistance * edgeDistance){
					return
				}
				CollisionResults results = new CollisionResults();
				Ray ray = new Ray(Converter.toJME(inner.position), Converter.toJME(difference.normalized))
				collidable.collideWith(ray, results)
				
				CollisionResult closest = results.closestCollision
				if(closest){
					if(closest.distance < difference.length){
						return
					}
				}
                inner.connect(outer)
			}
		}
	}
}
