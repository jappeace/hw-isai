package nl.jappieklooster.ISAI.behaviour.group

import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.behaviour.AbstractSteerable
import nl.jappieklooster.ISAI.entity.tracking.NeighbourTracker


abstract class ANeighbourAware extends AbstractSteerable{

	NeighbourTracker tracker
	float neighbourRadius
}
