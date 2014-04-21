package nl.jappieklooster.ISAI.entity.tracking

import nl.jappieklooster.ISAI.IWorldItem

/** keeps track of a entity and a distance */
class WorldItemDistance {

	float distance
	IWorldItem item
	
	WorldItemDistance(IWorldItem item, float dist){
		this.item = item
		distance = dist
	}

	@Override
	boolean equals(Object obj){
		if(obj.is(this)){
			return true
		}
		if(! obj instanceof WorldItemDistance){
			return false
		}
		WorldItemDistance idist = (WorldItemDistance) obj
		return idist.item == item && idist.distance == distance
	}
	
	@Override
	int hashCode(){
		(distance.hashCode() + item.hashCode()).hashCode()
	}
}
