package nl.jappieklooster.ISAI.entity.tracking

import nl.jappieklooster.ISAI.World;
import nl.jappieklooster.ISAI.entity.Entity
import nl.jappieklooster.ISAI.IWorldItem

/** this thing is an optimization (ugly code gauranteed) 
 * the intial implementation did a lot of dubble work per tick, this
 * class centralises that work in the hope it will be done ones per tick.
 * 
 * This will only work with proper initilization
 * */
class NeighbourTracker {

	/** the world to read from */
	World world
	
	private SortedSet<Distance> distances

	/** the resulting neighbours are stored in here */
	private Map<WorldItemDistance, List<IWorldItem>> neighbuffer
	
	NeighbourTracker(){
		distances = new TreeSet<>()
	}

	/** redetrimens which neigbours are where and stores that result into the neighbuffer */
	void reset(){
		distances.each{
			it.isUsed = false
		}
		
		// clear the buffer
		neighbuffer = new HashMap<>()
		
		world.entities.each{ IWorldItem against ->
			
			// TODO: divide and conquer
			world.entities.each{ IWorldItem to ->
                if(against == to){
                    return
                }

				distances.each{ Distance dist ->
					
                    addToKey(against, to, dist.distance)
				}
			}
		}
		
		// cleanup distances
		Iterator<Distance> iter = distances.iterator()
		while(iter.hasNext()){
			Distance d = iter.next()
			if(!d.isUsed){
				iter.remove()
			}
		}
	}
	
	// helper method because reset became a mes
	private void addToKey(IWorldItem against, IWorldItem to, float distance){

        if((against.position - to.position).lengthSq < distance*distance){
            def key = new WorldItemDistance(to, distance)
            List<IWorldItem> list
            if(!neighbuffer.containsKey(key)){
                list = new LinkedList<>()
                neighbuffer.put(key, list)
            }else{
                list = neighbuffer.get(key)
            }
            list.add(against)
        }
	}
	
	List<IWorldItem> getNeighbours(IWorldItem to, float distance){
		neighbuffer.get(new WorldItemDistance(to, distance)) ?: new LinkedList<>()
	}
	
	void addDistance(float which){
		distances.add(new Distance(which))
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
		
		
		return result
	}
}
