package nl.jappieklooster.ISAI.collection.graph

import com.jme3.scene.Node
import nl.jappieklooster.ISAI.world.AHasNode
import nl.jappieklooster.ISAI.collection.oct.OctTree
import nl.jappieklooster.math.vector.Vector3

class Graph extends AHasNode{

    private Vector3 min
    private Vector3 max
    Collection<Vertex> verteci

	Graph(){
		super()
        min = new Vector3(0)
        max = new Vector3(0)	
        verteci = new LinkedList<>() // reserve some space, you don't make a graph for 10 elements
	}
	
	/**
	 * utitlity function that attaches the vertex to the list and also adds the debugshape of the vertex to this graph
	 * @param what
	 */
	void add(Vertex what){
		verteci.add(what)
        node.attachChild(what.node)
		
		// update bounding box
        min.assimilateMin(what.position)
        max.assimilateMax(what.position)
	}
	
	void connect(Vertex from, Vertex to){
		if(!verteci.contains(from)){
			add(from)
		}
		if(!verteci.contains(to)){
			add(to)
		}
		from.connect(to)
	}
	/**
	 * creates an octree that wrap precisley arround the graphs edges
	 * @return
	 */
	OctTree toOctTree(){
		OctTree result = new OctTree(
			(max + min) / new Vector3(2) ,
			((max - min) / new Vector3(2) ) 
                * new Vector3(1.001) // times a small number to prevent edge case falling outside the tree
        )
		result.addAll(verteci)
		return result
	}
	
}
