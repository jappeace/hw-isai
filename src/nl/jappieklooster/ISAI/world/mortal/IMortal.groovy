package nl.jappieklooster.ISAI.world.mortal

/**
 * objects that can die should implement this interface
 * the general contract is, that if the float value is below zero, the object dies
 * @author jappie
 *
 */
interface IMortal {
	void setHealth(float to)
}
