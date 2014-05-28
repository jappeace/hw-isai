package nl.jappieklooster.ISAI.world.entity.compare

import nl.jappieklooster.ISAI.world.IPositionable;
import nl.jappieklooster.math.vector.IVector2
import nl.jappieklooster.math.vector.compareStrategies.ZComparator

class ComparatorIWorldItemZ implements Comparator<IPositionable>{

	private static ZComparator comparator = new ZComparator()

	@Override
	public int compare(IPositionable one, IPositionable two) {
		comparator.compare(one.position, two.position)
	}

}
