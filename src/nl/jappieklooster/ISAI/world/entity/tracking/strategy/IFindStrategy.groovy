package nl.jappieklooster.ISAI.world.entity.tracking.strategy

import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.PositionDistance;
import nl.jappieklooster.math.vector.Vector3

interface IFindStrategy {
	Map<PositionDistance, Collection<IPositionable>> find(SortedSet<Distance> tresholds)
	void setTargetItems(Collection<IPositionable> targetItems)

}
