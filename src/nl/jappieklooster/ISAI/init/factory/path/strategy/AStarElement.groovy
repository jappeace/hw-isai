package nl.jappieklooster.ISAI.init.factory.path.strategy

import nl.jappieklooster.ISAI.collection.graph.Vertex

/**
 * allows a score to be attached to a vertex
 * @author jappie
 */
class AStarElement implements Comparable<AStarElement>{

	AStarElement(Vertex vert, float score){
		this(null, vert, score)
	}
	AStarElement(AStarElement from, Vertex vert, float score){
		vertex = vert
		this.from = from
		this.score = score
	}
	float score
	/**
	 * the current node
	 */
	Vertex vertex
	
	/**
	 * where we came from (the actual path if followed recursive)
	 */
	AStarElement from
	@Override
	public int compareTo(AStarElement rhs) {
		if(this.score < rhs.score){
			return -1
		}
		if(this.score > rhs.score){
			return 1
		}
		return 0;
	}


}
