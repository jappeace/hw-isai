package nl.jappieklooster.ISAI.behaviour.state

import com.jme3.scene.Spatial

/**
 * provides a contract for the dsl of what to get out of the reference inside the statemachine
 * 
 * I could've used a template but this is less messy, besides this clearly specifies what the user code can acces
 * trough the states
 * @author jappie
 *
 */
interface IStateMachineTarget{
	Spatial getSpatial()
}
