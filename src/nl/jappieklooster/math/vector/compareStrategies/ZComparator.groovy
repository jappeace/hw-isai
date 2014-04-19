package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.*

class ZComparator extends BaseComparator implements Comparator<Vector3>{

	@Override
	public int compare(Vector3 one, Vector3 two) {
		return finalStep(one.z - two.z)
		
	}
}
