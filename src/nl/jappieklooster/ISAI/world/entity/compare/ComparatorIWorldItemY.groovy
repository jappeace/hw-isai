package nl.jappieklooster.ISAI.world.entity.compare

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.math.vector.IVector2
import nl.jappieklooster.math.vector.compareStrategies.XComparator
import nl.jappieklooster.math.vector.compareStrategies.YComparator

class ComparatorIWorldItemY implements Comparator<IPositionable>{

	private static YComparator comparator = new YComparator()

	@Override
	public int compare(IPositionable one, IPositionable two) {
		comparator.compare(one.position, two.position)
	}

}
