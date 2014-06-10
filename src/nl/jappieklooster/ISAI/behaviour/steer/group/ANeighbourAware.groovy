package nl.jappieklooster.ISAI.behaviour.steer.group

import nl.jappieklooster.ISAI.behaviour.steer.AbstractSteeringBehaviour;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.tracking.NeighbourTracker;


abstract class ANeighbourAware extends AbstractSteeringBehaviour{

	NeighbourTracker tracker
	float neighbourRadius
}
