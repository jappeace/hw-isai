package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.IVector2

class XComparator extends BaseComparator implements Comparator<IVector2>{

	@Override
	public int compare(IVector2 one, IVector2 two) {
		return safeFinalStep(one.x - two.x, one, two)
	}

}
