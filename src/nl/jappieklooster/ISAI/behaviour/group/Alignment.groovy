package nl.jappieklooster.ISAI.behaviour.group

import nl.jappieklooster.ISAI.IWorldItem
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.behaviour.AbstractSteerable;
import nl.jappieklooster.ISAI.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3

class Alignment extends ANeighbourAware{

	@Override
	public void steer() {
		List<IWorldItem> neighbours = world.findNeighbours(entity, neighbourRadius,{
			IWorldItem item ->
			// gaurantee only movingentities are returned
			item instanceof MovingEntity
		})
		
		// prevent eventual division by zero
		if(neighbours.size() == 0){
			return
		}
		
		Vector3 averageHeading = new Vector3()
		neighbours.each{
			
			// because of previous gaurantee I can now safely cast
			MovingEntity current = (MovingEntity) it
			averageHeading += current.heading
		}
		
		entity.force += (averageHeading / new Vector3(neighbours.size())) * power


	}

}
