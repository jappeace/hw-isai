package nl.jappieklooster.ISAI
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.jme3.scene.shape.Sphere;

import nl.jappieklooster.ISAI.entity.Entity
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter
class World implements IWorldItem{
	Node node
	List<IWorldItem> entities
	void update(float tpf){
		entities.each{
			it.update(tpf)
		}
	}
	/** local position*/
	public Vector3 getPosition() {
		return Converter.fromJME(node.localTranslation)
	}
	
	/**
	 * find stuff in this world
	 * @param to: where to start measuring from
	 * @param distance: the minimum distance to consider somthing a neighbour
	 * @param test: an extra filter
	 * @return
	 */
	List<IWorldItem> findNeighbours(IWorldItem to, float distance, Closure test = null){
		List<IWorldItem> result = new ArrayList<>()
		
		entities.each{
			if(it == to){
				return
			}
			if(test){
				if(!test(it)){
					return
				}
			}
			if((it.position - to.position).lengthSq < distance*distance){
				result.add(it)
			}
		}
		
		return result
	}

}
