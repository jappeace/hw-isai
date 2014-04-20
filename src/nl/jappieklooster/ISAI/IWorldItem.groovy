package nl.jappieklooster.ISAI

import nl.jappieklooster.math.vector.Vector3

interface IWorldItem {
	void update(float tpf);
	Vector3 getPosition()
}
