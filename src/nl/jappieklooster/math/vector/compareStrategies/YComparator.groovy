package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.IVector2

class YComparator extends BaseComparator implements Comparator<IVector2>{

	@Override
	public int compare(IVector2 one, IVector2 two) {
		return finalStep(one.y - two.y)
		
	}
}
