package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.IVector2

class YComparator implements Comparator<IVector2>{

	@Override
	public int compare(IVector2 one, IVector2 two) {
		return Float.compare(one.y, two.y)
		
	}
}
