package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.IVector2

class TwoWayComparator implements Comparator<IVector2>{

	@Override
	public int compare(IVector2 one, IVector2 two) {
		/** a big problem is how to differentiate the same data type double x from y, so I just take always the square root of X and do nothing with Y */
		return Double.compare(
			// the y will go trough sinus to fix the cross problem (vector(2,3) != vector(3,2))
			// also keep multiply by y to keep the comparotor natural ie vector(2, -3) < vector(3, -2)
			Math.sin(one.y) * one.y * one.x,
			Math.sin(two.y) * two.y * two.x
		)
		
	}

}
