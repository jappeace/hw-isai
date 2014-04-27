package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3


interface ISteerable {
	
	void steer()
	void setEntity(MovingEntity to)
	float chance
}
