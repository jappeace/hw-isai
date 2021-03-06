package nl.jappieklooster.ISAI.world.entity.tracking

import java.util.concurrent.Future
import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.IUpdatable;
import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.tracking.strategy.BruteForceStrategy;
import nl.jappieklooster.ISAI.world.entity.tracking.strategy.DivideAndConquer;
import nl.jappieklooster.ISAI.world.entity.tracking.strategy.IFindStrategy;
import nl.jappieklooster.ISAI.world.entity.tracking.threading.LazyPositionCopyMachine
import nl.jappieklooster.ISAI.world.entity.tracking.threading.ThreadResult;
import nl.jappieklooster.ISAI.world.entity.tracking.threading.TrackingThread;
import nl.jappieklooster.math.vector.Vector3

/** this thing is an optimization (ugly code gauranteed) 
 * the intial implementation did a lot of dubble work per tick, this
 * class centralises that work in the hope it will be done ones per tick.
 * 
 * it also puts the logic on a thread, so this thing does not slow down gameplay
 * it will cash then the results and start another thread
 * 
 * */
class NeighbourTracker implements IUpdatable{

	/** the world to read from */
	Group group
	
	IFindStrategy strategy
	
	/** a set of distances */
	private SortedSet<Distance> distances

	/** the resulting neighbours are stored in here */
	private Map<PositionDistance, List<IPositionable>> neighbuffer
	
	/** I should put this in a central place, this class is central for each world and no other class needs actualy acces to threads */
	private TrackingThread threadLogic
	private Future future
	
	private ScheduledThreadPoolExecutor threadPoolExecuter
	
	
	NeighbourTracker(ScheduledThreadPoolExecutor threadPool){
		threadPoolExecuter = threadPool
		distances = new TreeSet<>()
		strategy = new DivideAndConquer()
		neighbuffer = new HashMap<PositionDistance, List<IPositionable>>()
		
		threadLogic = new TrackingThread()
	}


	List<IPositionable> getNeighbours(IPositionable to, float distance){
		return neighbuffer[new PositionDistance(to, distance)] ?: new LinkedList<>()
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
			threadLogic.group = group
			future = threadPoolExecuter.submit(threadLogic)
		}
		// thread might be done already
		if(future.isDone()){

            ThreadResult result = (ThreadResult)future.get()
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
