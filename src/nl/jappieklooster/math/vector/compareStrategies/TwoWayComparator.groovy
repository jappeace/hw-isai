package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.Vector2

class TwoWayComparator extends BaseComparator implements Comparator<Vector2>{

	@Override
	public int compare(Vector2 one, Vector2 two) {
		/** a big problem is how to differentiate the same data type double x from y, so I just take always the square root of X and do nothing with Y */
		return finalStep(
			// the y will go trough sinus to fix the cross problem (vector(2,3) != vector(3,2))
			// also keep multiply by y to keep the comparotor natural ie vector(2, -3) < vector(3, -2)
			Math.sin(one.y) * one.y * one.x - // <- note the minus
			Math.sin(two.y) * two.y * two.x
		)
		
	}

}
