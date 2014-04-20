package nl.jappieklooster.ISAI.behaviour.group

import nl.jappieklooster.ISAI.IWorldItem
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.behaviour.AbstractSteerable;
import nl.jappieklooster.ISAI.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3

class Cohesion extends ANeighbourAware{

	@Override
	public void steer() {
		List<IWorldItem> neighbours = world.findNeighbours(entity, neighbourRadius)
		
		// prevent eventual division by zero
		if(neighbours.size() == 0){
			return
		}
		
		Vector3 centerOfMass = new Vector3()
		neighbours.each{
			centerOfMass += it.position
			
		}
		
		entity.force += 
		(
			(
				(
					centerOfMass / new Vector3(neighbours.size())) - entity.position
            ) 
                * new Vector3(entity.maxSpeed) - entity.velocity
		) * power
	}

}
