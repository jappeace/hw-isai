package nl.jappieklooster.ISAI.behaviour;

import nl.jappieklooster.ISAI.world.entity.MovingEntity

interface IBehaviour {
	void execute()
	void setEntity(MovingEntity to)
	float chance
}
