package nl.jappieklooster.ISAI.entity.tracking.strategy

import nl.jappieklooster.ISAI.IUpdatable
import nl.jappieklooster.ISAI.World;
import nl.jappieklooster.ISAI.entity.Entity
import nl.jappieklooster.ISAI.entity.tracking.Distance
import nl.jappieklooster.ISAI.entity.tracking.WorldItemDistance
import nl.jappieklooster.ISAI.IWorldItem

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
	Map<WorldItemDistance, List<IWorldItem>> find(SortedSet<Distance> tresholds){
		
		// clear the buffer
		result = new HashMap<>()
		
		targetItems.each{ IWorldItem first ->
			
			targetItems.each{ IWorldItem second ->
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
