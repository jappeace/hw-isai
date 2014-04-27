package nl.jappieklooster.ISAI.behaviour

import nl.jappieklooster.ISAI.world.entity.MovingEntity;
import nl.jappieklooster.math.vector.Vector3

class Explore extends AbstractSteerable{

	private Seek seek
	
	@Override
	void setEntity(MovingEntity ent){
		super.setEntity(ent)
		seek.entity = ent
	}
	void setPower(Vector3 to){
		super.setPower(to)
		seek.power = to
	}
	
	Explore(){
		seek = new Seek()
        destination = new Vector3()
		seek.getToCallback = {
			destination
		}
		prevFib = 0
		fibonacci = 1
	}
	
	private int prevFib
	private int fibonacci
	private Vector3 destination
	private int nr = 0
	@Override
	public void steer() {
		
		if((entity.position - destination).lengthSq > 10){
			seek.steer()
			return
		}
		nr = (nr + 1) % 4
		switch(nr){
			case 0:
				destination.z = -fibonacci
			break
			case 1:
				destination.x = -fibonacci
			break
			case 2:
				destination.z = fibonacci
			break
			case 3:
				int tmp = prevFib
				prevFib = fibonacci
				fibonacci += tmp
				destination.x = fibonacci
			break
		}
		seek.steer()
	}

}
