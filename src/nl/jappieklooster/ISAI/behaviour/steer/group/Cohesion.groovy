package nl.jappieklooster.ISAI.behaviour.steer.group

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.behaviour.steer.Seek;
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.ISAI.Util
import nl.jappieklooster.math.vector.Vector3

class Cohesion extends ANeighbourAware{

	private Seek seek = new Seek()
	
	@Override
	void setEntity(MovingEntity ent){
		super.setEntity(ent)
		seek.entity = ent
	}
	public void execute() {
		Vector3 centerofMass = Util.averageOfPositionables(
			tracker.getNeighbours(entity, neighbourRadius)
		)
		
		seek.toPosition = {
			return centerofMass
		}
		seek.execute()
	}

}

