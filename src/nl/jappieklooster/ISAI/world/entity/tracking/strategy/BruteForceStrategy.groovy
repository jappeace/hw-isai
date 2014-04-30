package nl.jappieklooster.ISAI.world.entity.tracking.strategy

import nl.jappieklooster.ISAI.world.IUpdatable;
import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.WorldItemDistance;

/** this thing is an optimization (ugly code gauranteed) 
 * the intial implementation did a lot of dubble work per tick, this
 * class centralises that work in the hope it will be done ones per tick.
 * 
 * This will only work with proper initilization
 * */
class BruteForceStrategy extends AbstractStrategy{

	BruteForceStrategy(){
		super()
		targetItems = new LinkedList<>()
	}

	/** redetrimens which neigbours are where and stores that result into the neighbuffer */
	Map<WorldItemDistance, List<IGroupItem>> find(SortedSet<Distance> tresholds){
		
		// clear the buffer
		result = new HashMap<>()
		
		targetItems.each{ IGroupItem first ->
			
			targetItems.each{ IGroupItem second ->
                if(first == second){
                    return
                }

				for(Distance dist : tresholds){
                    if((first.position - second.position).lengthSq > dist.distance*dist.distance){
						return
                    }
                    addToListValue(first, dist.distance, second)
				}
			}
		}
		return result
	}
}
