package nl.jappieklooster.ISAI.behaviour.group

import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.behaviour.AbstractSteerable


abstract class ANeighbourAware extends AbstractSteerable{

	World world
	float neighbourRadius
}
