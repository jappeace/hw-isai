package nl.jappieklooster.ISAI.world.entity.graph

import nl.jappieklooster.ISAI.world.entity.Entity
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import nl.jappieklooster.ISAI.init.factory.WireFrameFactory
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

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

	Vertex(Vector3 pos){
		super()
		position = pos
		connections = new LinkedList<>() // memory effiecient and index acces not required
		node = new Node("vertex node")
		geometry = WireFrameFactory.getInstance().createSphere()
		node.attachChild(geometry)
	}
	
	void setPosition(Vector3 to){
		super.setPosition(to)
		node.setLocalTranslation(Converter.toJME(to))
	}
	
	void connect(Vertex to){
		Edge edge = new Edge(to: to)
		connections.add(edge)

		Vector3 distance = this.position - to.position
		Vector3 conpos = distance / new Vector3(2) + this.position

		Geometry edgeConnection = WireFrameFactory.getInstance().createCube(new Vector3(distance, 1,1))

		edgeConnection.setLocalTranslation(Converter.toJME(conpos))
		
		// i have no idea, it has to do with the molecules, nobody knows what the sine function does, just go with it
		edgeConnection.rotate(
			0, 
			Math.asin((position.z - to.position.z) / distance.length), 
			Math.asin((position.y - to.position.y) / distance.length)
        )
		
		node.attachChild(edgeConnection)
	}
}
