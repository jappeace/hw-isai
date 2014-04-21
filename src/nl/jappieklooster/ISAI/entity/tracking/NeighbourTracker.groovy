package nl.jappieklooster.ISAI.entity.tracking

import nl.jappieklooster.ISAI.IUpdatable
import nl.jappieklooster.ISAI.World;
import nl.jappieklooster.ISAI.entity.Entity
import nl.jappieklooster.ISAI.entity.tracking.strategy.BruteForceStrategy
import nl.jappieklooster.ISAI.entity.tracking.strategy.DivideAndConquer
import nl.jappieklooster.ISAI.entity.tracking.strategy.IFindStrategy
import nl.jappieklooster.ISAI.IWorldItem

/** this thing is an optimization (ugly code gauranteed) 
 * the intial implementation did a lot of dubble work per tick, this
 * class centralises that work in the hope it will be done ones per tick.
 * 
 * This will only work with proper initilization
 * */
class NeighbourTracker implements IUpdatable{

	/** the world to read from */
	World world
	
	IFindStrategy strategy
	
	/** a set of distances */
	private SortedSet<Distance> distances

	/** the resulting neighbours are stored in here */
	private Map<WorldItemDistance, List<IWorldItem>> neighbuffer
	
	NeighbourTracker(){
		distances = new TreeSet<>()
		strategy = new DivideAndConquer()
	}

	/** redetrimens which neigbours are where and stores that result into the neighbuffer */
	void reset(){
        distances.each{
            it.isUsed = false
        }
		// tell strategy about the world
		strategy.targetItems = world.entities

		// clear the buffer
		neighbuffer = strategy.find(distances)
		
		// cleanup distances
		Iterator<Distance> iter = distances.iterator()
		while(iter.hasNext()){
			Distance d = iter.next()
			if(!d.isUsed){
				iter.remove()
			}
		}
	}

	List<IWorldItem> getNeighbours(IWorldItem to, float distance){
		neighbuffer.get(new WorldItemDistance(to, distance)) ?: new LinkedList<>()
	}
	
	void addDistance(float which){
		distances.add(new Distance(which))
	}

	@Override
	public void update(float tpf) {
		reset()
	}
}
