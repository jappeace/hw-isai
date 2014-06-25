package nl.jappieklooster.ISAI.collection.graph

import com.jme3.scene.Node
import nl.jappieklooster.ISAI.world.AHasNode
import nl.jappieklooster.ISAI.collection.oct.OctTree
import nl.jappieklooster.math.vector.Vector3

class Graph extends AHasNode{

    private Vector3 min
    private Vector3 max
    Collection<Vertex> verteci
	private OctTree cachedOctTree

	Graph(){
		super()
        min = Vector3.CreatePositiveInfinite()
        max = Vector3.CreateNegativeInfinite()	

        verteci = new LinkedList<>() // reserve some space, you don't make a graph for 10 elements
		clearCache()
	}
	
	/**
	 * utitlity function that attaches the vertex to the list and also adds the debugshape of the vertex to this graph
	 * @param what
	 */
	void add(Vertex what){
		clearCache()
		verteci.add(what)
        node.attachChild(what.spatial)
		
		// update bounding box
        min.assimilateMin(what.position)
        max.assimilateMax(what.position)
		int i =0
	}
	
	/**
	 * merge two graphs
	 * @param graph
	 */
	Graph merge(Graph graph){
		clearCache()
		node.attachChild(graph.node)
		graph.verteci.each{
			add(it)
		}
		return this
	}
	
	void connect(Vertex from, Vertex to){
		/*
		 * no need to clear the cache, because adding connections
		 * does not change the verteci positions
		 */
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
	 * this method is cached
	 * @return
	 */
	OctTree toOctTree(){
		if(cachedOctTree){
			return cachedOctTree
		}
		if(max == Vector3.CreateNegativeInfinite() || min == Vector3.CreatePositiveInfinite()){
			cachedOctTree = OctTree.createEmpty()
		}
		cachedOctTree = new OctTree(
			(max + min) / new Vector3(2) ,
			((max - min) / new Vector3(2) ) 
                * new Vector3(1.001) // times a small number to prevent edge case falling outside the tree
        )
		cachedOctTree.addAll(verteci)
		return cachedOctTree
	}
	
	private void clearCache(){
		cachedOctTree = null
	}
	
}
