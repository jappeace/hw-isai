package nl.jappieklooster.ISAI.world.entity.tracking.strategy

import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.ISAI.world.entity.tracking.Distance;
import nl.jappieklooster.ISAI.world.entity.tracking.WorldItemDistance;
import nl.jappieklooster.math.vector.Vector3

interface IFindStrategy {
	Map<WorldItemDistance, List<IGroupItem>> find(SortedSet<Distance> tresholds)
	void setTargetItems(List<IGroupItem> targetItems)
}
