package nl.jappieklooster.ISAI.world.entity.tracking.strategy

import nl.jappieklooster.ISAI.world.IUpdatable;
import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemX;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemY;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemZ;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.PositionDistance;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Dimension

/** commen base for bruteforce and divide and conquer strategies
 * */
abstract class AbstractStrategy implements IFindStrategy{
	/** the world to read from */
	Collection<IPositionable> targetItems
	protected Map<PositionDistance, Collection<IPositionable>> result

	AbstractStrategy(){
		targetItems = new LinkedList<>()
	}

	protected void addToListValue(IPositionable keyOne, float keyTwo, IPositionable value){

        def key = new PositionDistance(keyOne, keyTwo)
        List<IPositionable> list = result[key]

		if(list == null){
            list = new LinkedList<>()
			result[key] = list
		}

		// in both cases add the value to the list
        list.add(value)
	}
}
