package nl.jappieklooster.ISAI.behaviour.steer.group

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;


abstract class ANeighbourAware extends AbstractBehaviour{

	NeighbourTracker tracker
	float neighbourRadius
}
