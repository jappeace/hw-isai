package nl.jappieklooster.ISAI.behaviour.steer;

import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.world.entity.MovingEntity

interface ISteeringBehaviour extends IBehaviour{
	void setEntity(MovingEntity to)
}
