package nl.jappieklooster.ISAI

import nl.jappieklooster.math.vector.Vector3

interface IWorldItem extends IUpdatable{
	int hashCode()
	Vector3 getPosition()
}
