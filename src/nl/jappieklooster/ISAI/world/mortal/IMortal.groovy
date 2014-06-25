package nl.jappieklooster.ISAI.world.mortal

import com.jme3.scene.Spatial
import nl.jappieklooster.ISAI.world.IPositionable

/**
 * objects that can die should implement this interface
 * the general contract is, that if the float value is below zero, the object dies
 * @author jappie
 *
 */
interface IMortal extends IPositionable{
	void setHealth(float to)
	Spatial getBody()
}
