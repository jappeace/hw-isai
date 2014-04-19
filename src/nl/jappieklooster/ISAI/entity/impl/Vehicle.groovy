package nl.jappieklooster.ISAI.entity.impl

import nl.jappieklooster.ISAI.behaviour.ISteerable
import nl.jappieklooster.ISAI.entity.MovingEntity
import nl.jappieklooster.math.vector.Converter;

class Vehicle extends MovingEntity {
	List<ISteerable> steeringBehaviours

	Vehicle(){
		steeringBehaviours = new ArrayList<>()
	}
	@Override
	void update(float tpf){
		steeringBehaviours.each{
			it.steer(tpf)
		}
		position += velocity
		geom.move(Converter.toJME(velocity))
	}
	
	void add(ISteerable behaviour){
		behaviour.setEntity(this);
		steeringBehaviours.add(behaviour)
	}
}

