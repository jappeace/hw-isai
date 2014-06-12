package nl.jappieklooster.ISAI.behaviour;

import nl.jappieklooster.ISAI.world.entity.MovingEntity

/**
 * a behaivoiur is somthing that can be executed by somthing
 * It should be just one thing. And it should not be to complex
 * @author jappie
 *
 */
interface IBehaviour{
	
	/**
	 * somtimes its desirable to not let behaviours be exeucted evreytime. that what the cance is for
	 */
	float chance
	
	/**
	 * calling this method will execute the behaviour
	 */
	void execute()
}
