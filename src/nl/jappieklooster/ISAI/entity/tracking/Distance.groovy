package nl.jappieklooster.ISAI.entity.tracking

/**
 * keeps track of a float and if it has been used
 * @author jappie
 *
 */
class Distance implements Comparable<Distance>{

	float distance
	boolean isUsed
	Distance(float dist){
		distance = dist
        isUsed = false
	}

	float getDistance(){
		isUsed = true
		return distance
	}

	@Override
	public int compareTo(Distance rhs) {
		(int) distance - rhs.distance
	}

}
