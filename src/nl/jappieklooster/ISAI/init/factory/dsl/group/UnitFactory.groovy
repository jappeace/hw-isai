package nl.jappieklooster.ISAI.init.factory.dsl.group

import nl.jappieklooster.ISAI.init.factory.dsl.AMovingEntityFactory
import nl.jappieklooster.ISAI.world.entity.MovingEntity
import nl.jappieklooster.ISAI.world.entity.Unit
import nl.jappieklooster.math.vector.Vector3

class UnitFactory extends AMovingEntityFactory{
	Unit unit

	UnitFactory(){
		super()
		unit = new Unit()
		unit.velocity = new Vector3()
		unit.position = new Vector3()
	}
	
	

	@Override
	public MovingEntity getMovingEntity() {
		return unit
	}
}
