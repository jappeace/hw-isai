package nl.jappieklooster.math.vector.compareStrategies

abstract class BaseComparator{
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
	int finalStep(double result){
		return finalStep((float) result)
	}
	/**
	 * java std has an anoying habbit of checking 0 results if the objects are truly equal
	 * this safeFinalStep does the same, otherwise it gives a different result
	 * 
	 * this is a workaround
	 * @param result
	 * @param one
	 * @param two
	 * @return
	 */
	int safeFinalStep(float result, Object one, Object two){
		if(result == 0){
			if(one == two){
				return 0
			}
			return 1
		}
		return finalStep(result)
	}
	int safeFinalStep(double result, Object one, Object two){
		return safeFinalStep((float)result, one, two)
	}

}
