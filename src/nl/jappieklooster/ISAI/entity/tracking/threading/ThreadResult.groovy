package nl.jappieklooster.ISAI.entity.tracking.threading

import nl.jappieklooster.ISAI.IWorldItem
import nl.jappieklooster.ISAI.entity.tracking.Distance
import nl.jappieklooster.ISAI.entity.tracking.WorldItemDistance

/**
 * represents the result of trackingthread
 * @author jappie
 *
 */
class ThreadResult {
	SortedSet<Distance> distances
	Map<WorldItemDistance, List<IWorldItem>> neighbuffer
}
