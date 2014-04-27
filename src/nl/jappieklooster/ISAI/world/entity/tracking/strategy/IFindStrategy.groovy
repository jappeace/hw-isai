package nl.jappieklooster.ISAI.world.entity.tracking.strategy

import nl.jappieklooster.ISAI.world.IWorldItem;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.WorldItemDistance;
import nl.jappieklooster.math.vector.Vector3

interface IFindStrategy {
	Map<WorldItemDistance, List<IWorldItem>> find(SortedSet<Distance> tresholds)
	void setTargetItems(List<IWorldItem> targetItems)
}
