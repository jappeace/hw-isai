package nl.jappieklooster.ISAI.entity.tracking.strategy

import nl.jappieklooster.ISAI.IUpdatable
import nl.jappieklooster.ISAI.World;
import nl.jappieklooster.ISAI.entity.Entity
import nl.jappieklooster.ISAI.entity.compare.ComparatorIWorldItemX
import nl.jappieklooster.ISAI.entity.compare.ComparatorIWorldItemY
import nl.jappieklooster.ISAI.entity.compare.ComparatorIWorldItemZ
import nl.jappieklooster.ISAI.entity.tracking.Distance
import nl.jappieklooster.ISAI.entity.tracking.WorldItemDistance
import nl.jappieklooster.ISAI.IWorldItem
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Dimension

/** commen base for bruteforce and divide and conquer strategies
 * */
abstract class AbstractStrategy implements IFindStrategy{
	/** the world to read from */
	List<IWorldItem> targetItems
	protected Map<WorldItemDistance, List<IWorldItem>> result

	AbstractStrategy(){
		targetItems = new LinkedList<>()
	}

	protected void addToListValue(IWorldItem keyOne, float keyTwo, IWorldItem value){

        def key = new WorldItemDistance(keyOne, keyTwo)
        List<IWorldItem> list = result[key]

		if(list == null){
            list = new LinkedList<>()
			result[key] = list
		}

		// in both cases add the value to the list
        list.add(value)
	}
}
