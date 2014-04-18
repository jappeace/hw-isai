package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.Vector2

class XComparator extends BaseComparator{

	@Override
	public int compare(Vector2 one, Vector2 two) {
		// the least likley is equality
		return finalStep(one.x - two.x)
		
	}

}
