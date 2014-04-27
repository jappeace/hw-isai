package nl.jappieklooster.ISAI.behaviour.group

import nl.jappieklooster.ISAI.behaviour.AbstractSteerable;
import nl.jappieklooster.ISAI.behaviour.Seek
import nl.jappieklooster.ISAI.world.IWorldItem;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Cohesion extends ANeighbourAware{

	private Seek seek = new Seek()
	
	@Override
	void setEntity(MovingEntity ent){
		super.setEntity(ent)
		seek.entity = ent
	}
	public void steer() {
		List<IWorldItem> neighbours = tracker.getNeighbours(entity, neighbourRadius)
		
		// prevent eventual division by zero
		if(neighbours.size() == 0){
			return
		}
		
		Vector3 centerOfMass = new Vector3()
		neighbours.each{
			centerOfMass += it.position
			
		}
		
		seek.getToCallback = {
			centerOfMass / new Vector3(neighbours.size())
		}
		seek.steer()
	}

}
