package nl.jappieklooster.ISAI.world.entity.compare

import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.math.vector.IVector2
import nl.jappieklooster.math.vector.compareStrategies.XComparator
import nl.jappieklooster.math.vector.compareStrategies.YComparator

class ComparatorIWorldItemY implements Comparator<IGroupItem>{

	private static YComparator comparator = new YComparator()

	@Override
	public int compare(IGroupItem one, IGroupItem two) {
		comparator.compare(one.position, two.position)
	}

}
