package nl.jappieklooster.ISAI.world

import com.jme3.scene.Spatial

interface IHasSpatial extends IPositionable{
	Spatial getSpatial()
}
