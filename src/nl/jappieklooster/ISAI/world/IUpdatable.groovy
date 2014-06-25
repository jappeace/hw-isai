package nl.jappieklooster.ISAI.world

import nl.jappieklooster.math.vector.Vector3

/**
 * object implementing this interface should be notified when the game changes logical state
 * @author jappie
 *
 */
interface IUpdatable {
	void update(float tpf)
}
