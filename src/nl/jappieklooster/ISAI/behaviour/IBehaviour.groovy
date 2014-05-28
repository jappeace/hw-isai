package nl.jappieklooster.ISAI.behaviour;

import nl.jappieklooster.ISAI.world.entity.MovingEntity

interface IBehaviour{
	void setEntity(MovingEntity to)
	float chance
	void execute()
}
