package nl.jappieklooster.ISAI.collection

import com.jme3.bounding.BoundingBox
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector2
import nl.jappieklooster.math.vector.Vector3

class OctTree {

	// for bitwise magic
	private final int Xax = 4, Yax = 2, Zax = 1

	private Vector3 center
	private Vector3 halfDimension
	
	private OctTree[] children
	private IPositionable data
	
	OctTree(Vector3 center, Vector3 halfDimension){
		this.center = center
		this.halfDimension = halfDimension
		children = null
		data = null
	}
	
	void add(IPositionable element){
		if(isLeafNode()){
			
			// im a leaf and there is no data here
			if(data == null){
				data = element
				return
			}
			
			// Can only hold one data so tiem to construct children and let them do work
			children = new OctTree[8]
			(0..7).each{
				children[it] = new OctTree(
                    center + new Vector3(
                        halfDimension.x * (it & Xax ? 0.5 : -0.5),
                        halfDimension.y * (it & Yax ? 0.5 : -0.5),
                        halfDimension.z * (it & Zax ? 0.5 : -0.5)
                    ), 
                    halfDimension * new Vector3(0.5)
				)
			}

			children[getChildIndex(element.getPosition())].add(element)
			children[getChildIndex(data.getPosition())].add(data)
			
			// child now contains data, truncate own reference to prevent leaking
			data = null
			return
		}

		children[getChildIndex(element.getPosition())].add(element)
	}
	
	/**
	 * this finds somthing at a determenistic location, it has to be exaclty that location, otherwise it will return null
	 * @param location
	 * @return either the result at the exact location or null
	 */
	IPositionable get(Vector3 location){
		
		if(isLeafNode()){
			return data
		}
		return children[getChildIndex(location)].get(location)
	}
	
	Collection<IPositionable> find(Vector3 location, float radius){
        Collection<IPositionable> result = new LinkedList<>()
		if(isLeafNode()){
			if(data == null){
                return result
			}
			if((location - data.position).lengthSq < radius * radius){
				result.add(data)
			}
			return result
		}
		(0..7).each{
			Vector3 min = children[it].center - children[it].halfDimension + new Vector3(radius)
			Vector3 max = children[it].center + children[it].halfDimension - new Vector3(radius)
			
			if(min.x < location.x || min.y < location.y || min.z < location.z){
				return
			}
			
			if(max.x > location.x || max.y > location.y || max.z > location.z){
				return
			}
			result.addAll(children[it].find(location, radius))
		}
		return result
		
	}
	void forFound(Vector3 location, float radius, Closure action){
		if(isLeafNode()){
			if(data == null){
                return
			}
			if((location - data.position).lengthSq < radius * radius){
				action(data)
			}
			return
		}
		(0..7).each{
			Vector3 min = children[it].center - children[it].halfDimension + new Vector3(radius)
			Vector3 max = children[it].center + children[it].halfDimension - new Vector3(radius)
			
			if(min.x < location.x || min.y < location.y || min.z < location.z){
				return
			}
			
			if(max.x > location.x || max.y > location.y || max.z > location.z){
				return
			}
			children[it].forFound(location, radius, action)
		}
	}
	
	private boolean isLeafNode(){ children == null }
	/**
	 * figures out which child has the point
	 * @param point
	 * @return
	 */
	private int getChildIndex(Vector3 point) {
		int oct = 0;
		if(point.x >= center.x) oct |= Xax;
		if(point.y >= center.y) oct |= Yax;
		if(point.z >= center.z) oct |= Zax;
		return oct;
	}
}
