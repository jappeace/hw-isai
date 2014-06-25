package nl.jappieklooster.ISAI.world.mortal
import nl.jappieklooster.ISAI.world.IPositionable

/**
 * anything that can be used as weapon should impelment this interface.
 * It's basicly an attack factory
 * @author jappie
 *
 */
interface IWeapon {
	IAttack createAttack(IPositionable target)
}
