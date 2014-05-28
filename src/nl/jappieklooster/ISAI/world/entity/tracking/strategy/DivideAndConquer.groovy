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

import groovy.util.logging.*
/** this thing is an optimization (ugly code gauranteed) 
 * this uses spatial partitioning too optimize finding neighbours
 * the brutethreshold indicates when bruteforce method is initilized, the heigher the number
 * the more precise the result, but it also means more time is used
 * 
 * This will only work with proper initilization
 * */
@Log
class DivideAndConquer extends AbstractStrategy{
	private static final int bruteTreshold = 8
	private Comparator<IPositionable>[] comparators = [
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
	Map<PositionDistance, Collection<IPositionable>> find(SortedSet<Distance> tresholds){
		this.tresholds = tresholds
		// clear the buffer
		result = new HashMap<>()
		
		recursiveSolve(targetItems, Dimension.x.value)
		
		return result
	}
	
	Vector3 recursiveSolve(Collection<IPositionable> items, int xyz){
		if(items == null){
			return Vector3.max()
		}
		if(items.size() < 3){
			return tryAdd(items[0], items[1])
		}
		// adding this solves an anoying hunch in my mind and it is faster
		if(items.size() <= bruteTreshold){
			items.each{ IPositionable outer ->
				items.each{ IPositionable inner ->
					if(inner == outer){
						return
					}
					tryAdd(inner, outer)
				}
			}
		}
		
		xyz = (xyz + 1) % 3 // three dimensions
		
		try{
            Collections.sort(items, comparators[xyz])
		}catch(java.lang.IllegalArgumentException exeption){
		
			/*
			 * because i'm only comparing one part of the vector3 class
			 * the collections framework of java might trow an exception
			 * 
			 * I think this only happens when a comparator return 0, and java
			 * calls the equals method to verify, it would also return true
			 * if they would insert the right comparator, but they don't
			 * 
			 * my solution is just to ignore this part, it almost never happens
			 */
			log.info "ignoring illegal argument exception "
			return // java did not like this
		}
		
		List<List<IPositionable>> fracturedItems = items.toList().collate((int) ((items.size() / 2)))
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
	
	
	
	private Vector3 tryAdd(IPositionable one, IPositionable two){
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
