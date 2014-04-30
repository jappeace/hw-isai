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
		node = new Node("vertex node")
		geometry = WireFrameFactory.getInstance().createSphere(1)
		node.attachChild(geometry)
		position = pos
		connections = new LinkedList<>() // memory effiecient and index acces not required
	}
	
	void setPosition(Vector3 to){
		super.setPosition(to)
		node.setLocalTranslation(Converter.toJME(to))
	}
	
	void connect(Vertex to){
		Edge edge = new Edge(to: to)
		connections.add(edge)

		Vector3 difference =  this.position - to.position
		Vector3 conpos = to.position / new Vector3(2)

		Geometry edgeConnection = WireFrameFactory.getInstance().createCube(new Vector3(1, difference.length/2,1))

		edgeConnection.setLocalTranslation(Converter.toJME(conpos))
		
		// i have no idea, it has to do with the molecules, nobody knows what the sine function does, just go with it
		edgeConnection.rotateUpTo(Converter.toJME((difference - this.position).normalized))
		
		node.attachChild(edgeConnection)
	}
}
