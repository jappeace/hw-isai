package nl.jappieklooster.ISAI.world.entity.tracking.strategy

import nl.jappieklooster.ISAI.world.IUpdatable;
import nl.jappieklooster.ISAI.world.IWorldItem;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemX;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemY;
import nl.jappieklooster.ISAI.world.entity.compare.ComparatorIWorldItemZ;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.WorldItemDistance;
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Dimension

/** this thing is an optimization (ugly code gauranteed) 
 * the intial implementation did a lot of dubble work per tick, this
 * class centralises that work in the hope it will be done ones per tick.
 * 
 * This will only work with proper initilization
 * */
class DivideAndConquer extends AbstractStrategy{
	private static final int bruteTreshold = 8
	private Comparator<IWorldItem>[] comparators = [
		new ComparatorIWorldItemX(), 
		new ComparatorIWorldItemY(), 
		new ComparatorIWorldItemZ()
    ]
	private SortedSet<Distance> tresholds
	DivideAndConquer(){
		super()
		targetItems = new LinkedList<>()
	}

	/** redetermens which neighbours are where and stores that result into the result */
	Map<WorldItemDistance, List<IWorldItem>> find(SortedSet<Distance> tresholds){
		this.tresholds = tresholds
		// clear the buffer
		result = new HashMap<>()
		
		recursiveSolve(targetItems, Dimension.x.value)
		
		return result
	}
	
	Vector3 recursiveSolve(List<IWorldItem> items, int xyz){
		if(items == null){
			return Vector3.max()
		}
		if(items.size() < 3){
			return tryAdd(items[0], items[1])
		}
		// adding this solves an anoying hunch in my mind and it is faster
		if(items.size() <= bruteTreshold){
			items.each{ IWorldItem outer ->
				items.each{ IWorldItem inner ->
					if(inner == outer){
						return
					}
					tryAdd(inner, outer)
				}
			}
		}
		
		xyz = (xyz + 1) % 3 // three dimensions
		Collections.sort(items, comparators[xyz])
		
		List<List<IWorldItem>> fracturedItems = items.toList().collate((int) ((items.size() / 2)))
		Vector3 one = recursiveSolve(fracturedItems[0], xyz) ?: Vector3.max()
		Vector3 two = recursiveSolve(fracturedItems[1], xyz) ?: Vector3.max()
		
		// prevent sqrt (sort of, the vecctor2 still uses it)
		Vector3 shortest = one.lengthSq < two.lengthSq ? one : two
		
		// I added and [] acces to vector specialy for the following lines, which reduces code a lot
		// on a 2d plane I used an if to determin the x or y, but adding another case to that construct
		// seemed dumb
		double strip = Math.abs(shortest[xyz])
		double fieldWidth = items[items.size() -1].position[xyz]
		double start = fieldWidth / 2 - strip / 2
		double end = fieldWidth / 2 + strip / 2
		
		Vector3 stripLine = recursiveSolve(
			items.findAll{start > it.position[xyz] && end < it.position[xyz]}, 
			xyz
		) ?: Vector3.max()
		return stripLine.lengthSq < shortest.lengthSq ? stripLine : shortest
	}
	
	
	
	private Vector3 tryAdd(IWorldItem one, IWorldItem two){
		if(one == null || two == null){
			return Vector3.max()
		}
		Vector3 line = one.position - two.position
        for(Distance dist : tresholds){
            if(line.lengthSq > dist.distance*dist.distance){
                return
            }
            addToListValue(one, dist.distance, two)
            addToListValue(two, dist.distance, one)
        }
	}
}