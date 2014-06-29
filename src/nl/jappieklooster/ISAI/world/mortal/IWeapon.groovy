package nl.jappieklooster.ISAI.world.mortal
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.IUpdatable

/**
 * anything that can be used as weapon should impelment this interface.
 * It's basicly an attack factory
 * @author jappie
 *
 */
interface IWeapon  extends IUpdatable{
	/**
	 * creates an new attack or returns null when the weapon is not ready yet
	 * @param target
	 * @return
	 */
	IAttack attack(IPositionable target)
	
	/**
	 * the team set as target would receive damage
	 * @param to
	 */
	void setTargetTeam(Team to)
}
