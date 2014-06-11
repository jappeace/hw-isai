package nl.jappieklooster.ISAI.behaviour.steer.group

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Alignment extends ANeighbourAware{

	@Override
	public void execute() {
		List<IGroupItem> neighbours = tracker.getNeighbours(entity, neighbourRadius)
		
		// prevent eventual division by zero
		if(neighbours.size() == 0){
			return
		}
		
		Vector3 averageHeading = new Vector3()
		int size = neighbours.size()
		neighbours.each{
			if(!(it instanceof MovingEntity)){
				size--
				return
			}
			
			// because of previous gaurantee I can now safely cast
			MovingEntity current = (MovingEntity) it
			averageHeading += current.heading
		}
		
		if(size == 0){
			return
		}
		entity.force += averageHeading / new Vector3(size)
	}

}
