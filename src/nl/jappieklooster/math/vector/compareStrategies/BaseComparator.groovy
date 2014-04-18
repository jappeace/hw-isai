package nl.jappieklooster.math.vector.compareStrategies

import nl.jappieklooster.math.vector.Vector2

abstract class BaseComparator implements Comparator<Vector2>{
	/** handles the double to int conversion, without truncation*/
	int finalStep(float result){
		if(result < 0){
			return -1
		}else if(result > 0){
			return 1
		}
		// the least likley is equality
		return 0
	}

}
