package nl.jappieklooster.ISAI.behaviour.steer.group

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.behaviour.steer.Seek;
import nl.jappieklooster.ISAI.world.IGroupItem;
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
	public void execute() {
		List<IGroupItem> neighbours = tracker.getNeighbours(entity, neighbourRadius)
		
		// prevent eventual division by zero
		if(neighbours.size() == 0){
			return
		}
		
		Vector3 centerOfMass = new Vector3()
		neighbours.each{
			centerOfMass += it.position
			
		}
		
		seek.toPosition = {
			centerOfMass / new Vector3(neighbours.size())
		}
		seek.execute()
	}

}
