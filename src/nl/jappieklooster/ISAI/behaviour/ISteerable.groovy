package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3


interface ISteerable {
	
	void steer(float tpf)
	void setEntity(MovingEntity to)

}
