package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.Vector2

class YComparator extends BaseComparator{

	@Override
	public int compare(Vector2 one, Vector2 two) {
		return finalStep(one.y - two.y)
		
	}

}
