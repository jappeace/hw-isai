package nl.jappieklooster.ISAI.entity.tracking

import java.util.concurrent.Future
import java.util.concurrent.ScheduledThreadPoolExecutor
import nl.jappieklooster.ISAI.IUpdatable
import nl.jappieklooster.ISAI.World;
import nl.jappieklooster.ISAI.entity.Entity
import nl.jappieklooster.ISAI.entity.tracking.strategy.BruteForceStrategy
import nl.jappieklooster.ISAI.entity.tracking.strategy.DivideAndConquer
import nl.jappieklooster.ISAI.entity.tracking.strategy.IFindStrategy
import nl.jappieklooster.ISAI.entity.tracking.threading.ThreadResult
import nl.jappieklooster.ISAI.entity.tracking.threading.TrackingThread
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
	
	/** I should put this in a central place, this class is central for each world and no other class needs actualy acces to threads */
	private ScheduledThreadPoolExecutor threadPoolExecuter
	private TrackingThread threadLogic
	private static final int threadCount = 1
	private Future future
	
	
	
	NeighbourTracker(){
		distances = new TreeSet<>()
        threadPoolExecuter = new ScheduledThreadPoolExecutor(threadCount) // curently only neighbourtracker uses it
		strategy = new DivideAndConquer()
		neighbuffer = new HashMap<WorldItemDistance, List<IWorldItem>>()
		
		threadLogic = new TrackingThread()
	}


	List<IWorldItem> getNeighbours(IWorldItem to, float distance){
		neighbuffer.get(new WorldItemDistance(to, distance)) ?: new LinkedList<>()
	}
	
	void addDistance(float which){
		distances.add(new Distance(which))
	}

	/** redetrimens which neigbours are where and stores that result into the neighbuffer */
	@Override
	public void update(float tpf) {
		
		if(future == null){
			threadLogic.distances = distances.clone()
			threadLogic.strategy = strategy
			threadLogic.world = world
			future = threadPoolExecuter.submit(threadLogic)
		}
		// thread might be done already
		if(future.isDone()){
			ThreadResult result = future.get()
			neighbuffer = result.neighbuffer
			distances.retainAll(result.distances)
			future = null
			return
		}
		if(future.isCancelled()){
			future = null
		}

	}
}
