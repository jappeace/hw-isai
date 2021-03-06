package nl.jappieklooster.ISAI.world.entity.tracking.threading
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector3;
/**
 * copies a position of a IPositionable when the getPosition call is made,
 * evrey other call it will return the copy
 * 
 * this allows trheads to do sorting while using the position as late as possible,
 * making the data in the thread more relevant
 * @author jappie
 *
 */
class LazyPositionCopyMachine implements IPositionable{

	private Vector3 copy = null
	IPositionable source
	
	LazyPositionCopyMachine(IPositionable src){
		source = src
	}
	@Override
	Vector3 getPosition() {
		if(copy != null){
			return copy
		}
		copy = new Vector3(source.position)
		return copy
	}

	// this is a wrapper so this is legit
	@Override
	int hashCode(){
		return source.hashCode()
	}

	@Override
	boolean equals(Object obj){
		return source == obj
	}
}
