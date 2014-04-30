package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.*

class ZComparator extends BaseComparator implements Comparator<IVector3>{

	@Override
	public int compare(IVector3 one, IVector3 two) {
		return safeFinalStep(one.z - two.z, one, two)
		
	}
}
