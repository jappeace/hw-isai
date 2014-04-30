package nl.jappieklooster.ISAI.world.entity.tracking.threading

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.ScheduledThreadPoolExecutor

import nl.jappieklooster.ISAI.world.Group
import nl.jappieklooster.ISAI.world.IUpdatable;
import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.WorldItemDistance;
import nl.jappieklooster.ISAI.world.entity.tracking.strategy.BruteForceStrategy;
import nl.jappieklooster.ISAI.world.entity.tracking.strategy.DivideAndConquer;
import nl.jappieklooster.ISAI.world.entity.tracking.strategy.IFindStrategy;

/** this thing is an optimization (ugly code gauranteed) 
 * the intial implementation did a lot of dubble work per tick, this
 * class centralises that work in the hope it will be done ones per tick.
 * 
 * This will only work with proper initilization
 * */
class TrackingThread implements Callable<ThreadResult>{

	/** the world to read from, to clone this entirely would be to slow */
	Group group
	
	IFindStrategy strategy
	
	/** a set of distances */
	private SortedSet<Distance> distances
	
	void setDistances(SortedSet<Distance> to){
		distances = to
	}

	@Override
	public ThreadResult call() throws Exception {
		// start with marking distances as not used (we want to delete as many as possible to safe time)
	    distances.each{
            it.isUsed = false
        }
		// tell strategy about the world
		strategy.targetItems = group.entities

		// create the result
		ThreadResult result = new ThreadResult()
		result.neighbuffer = strategy.find(distances)
		
		// cleanup distances
		Iterator<Distance> iter = distances.iterator()
		while(iter.hasNext()){
			Distance d = iter.next()
			if(!d.isUsed){
				iter.remove()
			}
		}	
		result.distances = distances

		return result
	}
}
