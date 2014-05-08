package nl.jappieklooster.ISAI.init.factory

import com.jme3.collision.CollisionResult
import com.jme3.collision.CollisionResults
import com.jme3.math.Ray
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.terrain.geomipmap.TerrainQuad
import com.jme3.scene.Node

import nl.jappieklooster.ISAI.TaskSynchronizer
import nl.jappieklooster.ISAI.collection.graph.Graph;
import nl.jappieklooster.ISAI.collection.graph.Vertex;
import nl.jappieklooster.ISAI.collection.oct.OctTree
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
	private Closure connector
	TerrainNavGraphFactory(){
		random = new Random()
		graph = new Graph()
		graph.name += " navgraph for terrain"
		
		collidable = new Node("collision node for navgraph")
		// allows swapping on runtime
		connector = { Vertex inner, Vertex outer -> 
			inner.connect(outer)
        }
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
		
		OctTree<Vertex> tree = createOctTree()
		graph.verteci.each{ Vertex outer ->
			tree.search(outer.position, edgeDistance, { Vertex inner ->
				if(outer.position == inner.position){
					return
				}
				Vector3 difference = outer.position - inner.position
				CollisionResults results = new CollisionResults();
				Ray ray = new Ray(Converter.toJME(inner.position), Converter.toJME(difference.normalized))
				collidable.collideWith(ray, results)
				
				CollisionResult closest = results.closestCollision
				if(closest){
					if(closest.distance < difference.length){
						return
					}
				}
				connector(inner, outer)
			})
		}
	}

	void connectVerticiCloserThenAsync(float edgeDistance, TaskSynchronizer syncer){
		new Thread({
            Closure syncedLogic = connector
            connector = { Vertex inner, Vertex outer ->
                syncer.execute{
                    inner.connect(outer)
                }
            }
            connectVerticiCloserThen(edgeDistance)
            connector = syncedLogic
		}).start()
			
	}

	/**
	 * creates an octree that wrap precisley arround the graphs edges
	 * @return
	 */
	OctTree createOctTree(){
		Vector3 min = new Vector3(0)
		Vector3 max = new Vector3(0)
		
		graph.verteci.each{
			min.assimilateMin(it.position)
			max.assimilateMax(it.position)
		}

		OctTree result = new OctTree(
			(max + min) / new Vector3(2) ,
			((max - min) / new Vector3(2) ) * new Vector3(1.001))
		result.addAll(graph.verteci)
		return result
	}
}
