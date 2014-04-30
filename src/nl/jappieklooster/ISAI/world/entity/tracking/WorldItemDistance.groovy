package nl.jappieklooster.ISAI.world.entity.tracking

import nl.jappieklooster.ISAI.world.IGroupItem;

/** keeps track of a entity and a distance */
class WorldItemDistance {

	float distance
	IGroupItem item
	
	WorldItemDistance(IGroupItem item, float dist){
		this.item = item
		distance = dist
	}

	@Override
	boolean equals(Object obj){
		if(obj.is(this)){
			return true
		}
		if(! (obj instanceof WorldItemDistance)){
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
