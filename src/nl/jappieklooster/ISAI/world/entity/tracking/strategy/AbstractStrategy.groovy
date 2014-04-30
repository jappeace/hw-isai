package nl.jappieklooster.ISAI.world.entity.tracking.strategy

import nl.jappieklooster.ISAI.world.IUpdatable;
import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemX;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemY;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemZ;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.WorldItemDistance;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Dimension

/** commen base for bruteforce and divide and conquer strategies
 * */
abstract class AbstractStrategy implements IFindStrategy{
	/** the world to read from */
	List<IGroupItem> targetItems
	protected Map<WorldItemDistance, List<IGroupItem>> result

	AbstractStrategy(){
		targetItems = new LinkedList<>()
	}

	protected void addToListValue(IGroupItem keyOne, float keyTwo, IGroupItem value){

        def key = new WorldItemDistance(keyOne, keyTwo)
        List<IGroupItem> list = result[key]

		if(list == null){
            list = new LinkedList<>()
			result[key] = list
		}

		// in both cases add the value to the list
        list.add(value)
	}
}
