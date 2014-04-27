package nl.jappieklooster.ISAI.world.entity.compare

import nl.jappieklooster.ISAI.world.IWorldItem;
import nl.jappieklooster.math.vector.IVector2
import nl.jappieklooster.math.vector.compareStrategies.ZComparator

class ComparatorIWorldItemZ implements Comparator<IWorldItem>{

	private static ZComparator comparator = new ZComparator()

	@Override
	public int compare(IWorldItem one, IWorldItem two) {
		comparator.compare(one.position, two.position)
	}

}
