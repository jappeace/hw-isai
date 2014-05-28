package nl.jappieklooster.ISAI.world.entity.tracking.threading

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.PositionDistance;

/**
 * represents the result of trackingthread
 * @author jappie
 *
 */
class ThreadResult {
	SortedSet<Distance> distances
	Map<PositionDistance, List<IPositionable>> neighbuffer
}
