package nl.jappieklooster.ISAI.behaviour.group

import nl.jappieklooster.ISAI.behaviour.AbstractSteerable;
import nl.jappieklooster.ISAI.world.IGroupItem;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.math.vector.Vector3

class Seperation extends ANeighbourAware{

	@Override
	public void steer() {
		Vector3 force = new Vector3()

		tracker.getNeighbours(entity, neighbourRadius).each{

			Vector3 toAgent = entity.position - it.position
			force += toAgent.normalized / new Vector3(toAgent.length)

		}

		entity.force += force
	}

}
