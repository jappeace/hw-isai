package nl.jappieklooster.ISAI.world.entity.graph

import nl.jappieklooster.ISAI.world.entity.Entity
import com.jme3.scene.Geometry
import com.jme3.scene.Node
/**
 * A graph either contains nodes or vertecies
 * but since jme3 defines it own node and also the std groovy libraries I decided that
 * vertex problably is easier for this code base (less clashing)
 * @author jappie
 *
 */
class Vertex extends Entity{

	Node node
	List<Edge> connections

	Vertex(){
		connections = new LinkedList<>() // memory effiecient and index acces not required
		node = new Node("vertex node")
	}
	
	void connect(Vertex to){
		Edge edge = new Edge(to: to)
	}
}
