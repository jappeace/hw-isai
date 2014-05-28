package nl.jappieklooster.ISAI.behaviour;

import nl.jappieklooster.ISAI.world.entity.MovingEntity

interface IBehaviour extends IExecuteable{
	void setEntity(MovingEntity to)
	float chance
}
