package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.Vector2

class XComparator extends BaseComparator implements Comparator<Vector2>{

	@Override
	public int compare(Vector2 one, Vector2 two) {
		return finalStep(one.x - two.x)
		
	}

}
