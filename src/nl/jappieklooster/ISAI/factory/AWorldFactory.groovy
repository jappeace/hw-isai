package nl.jappieklooster.ISAI.factory

import com.jme3.asset.AssetManager

import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.entity.impl.Vehicle
import nl.jappieklooster.ISAI.entity.tracking.NeighbourTracker
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

import com.jme3.scene.Node
import com.jme3.scene.Spatial;

abstract class AWorldFactory{
	World world
	NeighbourTracker neighTracker
	AWorldFactory(World world, NeighbourTracker tracker){
        neighTracker = tracker
		this.world = world
	}
}
