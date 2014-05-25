package nl.jappieklooster.ISAI.collection.graph

import com.jme3.scene.Geometry
import nl.jappieklooster.ISAI.world.entity.Entity

/**
 * A connection to a verteci
 * one vertex has an edge which contains a reference to the other vertex
 * @author jappie
 *
 */
class Edge extends Entity{
	Vertex to
	/**
	 * the weight of the edge
	 */
	float weight = 1
}
