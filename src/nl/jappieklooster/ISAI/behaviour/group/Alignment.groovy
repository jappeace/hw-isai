package nl.jappieklooster.ISAI.behaviour.group

import nl.jappieklooster.ISAI.IWorldItem
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.behaviour.AbstractSteerable;
import nl.jappieklooster.ISAI.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3

class Alignment extends ANeighbourAware{

	@Override
	public void steer() {
		List<IWorldItem> neighbours = tracker.getNeighbours(entity, neighbourRadius)
		
		// prevent eventual division by zero
		if(neighbours.size() == 0){
			return
		}
		
		Vector3 averageHeading = new Vector3()
		int size = neighbours.size()
		neighbours.each{
			if(!it instanceof MovingEntity){
				size--
				return
			}
			
			// because of previous gaurantee I can now safely cast
			MovingEntity current = (MovingEntity) it
			averageHeading += current.heading
		}
		
		entity.force += (averageHeading / new Vector3(size))
	}

}
