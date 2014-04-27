package nl.jappieklooster.ISAI.world.entity.tracking.threading

import nl.jappieklooster.ISAI.world.IWorldItem;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.WorldItemDistance;

/**
 * represents the result of trackingthread
 * @author jappie
 *
 */
class ThreadResult {
	SortedSet<Distance> distances
	Map<WorldItemDistance, List<IWorldItem>> neighbuffer
}