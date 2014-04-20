package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.entity.MovingEntity
import nl.jappieklooster.math.vector.Vector3


interface ISteerable {
	
	void steer()
	void setEntity(MovingEntity to)
	void setPower(Vector3 to)

}
