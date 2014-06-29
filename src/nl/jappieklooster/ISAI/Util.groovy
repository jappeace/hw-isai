package nl.jappieklooster.ISAI

import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector3

/**
 * contains static methods that I just don't know where to put
 * @author jappie
 *
 */
class Util {

	static Vector3 averageOfPositionables(Iterable<IPositionable> vectors){
		Iterator<Vector3> iter = vectors.iterator()
		// prevent eventual division by zero
		if(!iter.hasNext()){
			return new Vector3(0)
		}
		
		Vector3 average = new Vector3()
		int num = 0
		vectors.each{
			num++
			average += it.position
			
		}

		return average / new Vector3(num)
	}
}
