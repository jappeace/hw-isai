package nl.jappieklooster.ISAI.world.entity.compare

import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.math.vector.IVector2
import nl.jappieklooster.math.vector.compareStrategies.XComparator

class ComparatorIWorldItemX implements Comparator<IGroupItem>{

	private static XComparator comparator = new XComparator()

	@Override
	public int compare(IGroupItem one, IGroupItem two) {
		comparator.compare(one.position, two.position)
	}

}
