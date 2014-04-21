package nl.jappieklooster.ISAI.entity.tracking

/**
 * keeps track of a float and if it has been used
 * natural ordering is from big to small
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
	int compareTo(Distance rhs) {
		(int) rhs.distance - distance
	}
	
	@Override
	boolean equals(Object to){
		if(this.is(to)){
			return true
		}
		if(! (to instanceof Distance)){
			return false
		}
		Distance dist = (Distance) to
		return dist.distance == distance && dist.isUsed == isUsed
	}
	

}
