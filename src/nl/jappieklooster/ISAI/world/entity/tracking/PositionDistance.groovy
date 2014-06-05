package nl.jappieklooster.ISAI.world.entity.tracking

import nl.jappieklooster.ISAI.world.IPositionable;

/** keeps track of a entity and a distance */
class PositionDistance {

	float distance
	IPositionable item
	
	PositionDistance(IPositionable item, float dist){
		this.item = item
		distance = dist
	}

	@Override
	boolean equals(Object obj){
		if(obj.is(this)){
			return true
		}
		if(! (obj instanceof PositionDistance)){
			return false
		}
		PositionDistance idist = (PositionDistance) obj
		return idist.item == item && idist.distance == distance
	}
	
	@Override
	int hashCode(){
		(distance.hashCode() + item.hashCode()).hashCode()
	}
}
