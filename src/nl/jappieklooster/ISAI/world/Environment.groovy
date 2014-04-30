package nl.jappieklooster.ISAI.world
import com.jme3.scene.Node
import com.jme3.scene.Spatial

import nl.jappieklooster.ISAI.world.entity.graph.Graph
import nl.jappieklooster.math.vector.Converter
import nl.jappieklooster.math.vector.Vector3

/**
 * an environment is in this case non interactive stuff, like the ground or some buildings.
 * the only interaction would be collision, which can be determend by a huge collision shape on the entire envoironmeent, which is fast
 * @author jappie
 *
 */
class Environment extends AHasNode{
	
	/**
	 * the graph is stored in my own graph implementation
	 */
	Graph navGraph
	
	Environment(){
		super()
		navGraph = new Graph()
	}

	@Override
	public Vector3 getPosition() {
		return Converter.fromJME(node.getWorldTranslation())
	}
}
