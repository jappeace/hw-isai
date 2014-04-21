package nl.jappieklooster.ISAI.entity.compare

import nl.jappieklooster.ISAI.IWorldItem
import nl.jappieklooster.math.vector.IVector2
import nl.jappieklooster.math.vector.compareStrategies.XComparator
import nl.jappieklooster.math.vector.compareStrategies.YComparator

class ComparatorIWorldItemY implements Comparator<IWorldItem>{

	private static YComparator comparator = new YComparator()

	@Override
	public int compare(IWorldItem one, IWorldItem two) {
		comparator.compare(one.position, two.position)
	}

}
