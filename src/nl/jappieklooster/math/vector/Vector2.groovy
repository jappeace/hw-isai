package nl.jappieklooster.math.vector

import nl.jappieklooster.math.vector.compareStrategies.TwoWayComparator

/**
 * this is a basic 2 double container with all the operator overloading that made sense, so no getat and putat but practicly evreything else
 * I chose doubles, because java is going to be slow anyways, so Might aswell store a bunch of data.
 * Besides this class is very handy in cartesian situations, which is considerd a 2D space, which gives the 2D a double meaning...
 * @author jappie
 *
 */
class Vector2 implements IVector2{
	static final double defaultValue = 0
	float x, y
	
	/** WARNING the compare strategy is not considerd to be part of the objectState
	* This means the indivudaul comparators and the equals methods don't take in acount which strategy is used
	* By default they all use the same (the bothcomparator)
	*/
	Comparator<IVector2> compareStrategy
	//////// constructors
	Vector2(){
		this(defaultValue,defaultValue)
	}
	/** copy constructor, because handy*/
	Vector2(Vector2 input){
		this(input.X, input.Y)
	}
	Vector2(double both){
		this((float) both)
	}
	Vector2(float both){
		this(both, both)
	}
	Vector2(double x, double y){
		this((float)x, (float)y)
	}
	Vector2(float x, float y){
		this.x = x
		this.y = y
		compareStrategy = new TwoWayComparator()
	}

	@Override
	Object clone(){
		// this class only contains primitives, so changing anything is unecisary, as specified in documentation
		return super.clone();
	}

	@Override
	String toString(){
		return "(" + x + "," + y + ")"
	}
	///////// aritmatics
	@Override
	Vector2 plus(Vector2 rhs){
		new Vector2(
			this.x + rhs.x,
			this.y + rhs.y
		)
	}
	@Override
	Vector2 minus(Vector2 rhs){
		new Vector2(
			this.x - rhs.x,
			this.y - rhs.y
		)
	}
	@Override
	Vector2 multiply(Vector2 rhs){
		new Vector2(
			this.x * rhs.x,
			this.y * rhs.y
		)
	}
	@Override
	Vector2 power(Vector2 rhs){
		new Vector2(
			this.x ** rhs.x,
			this.y ** rhs.y
		)
	}
	@Override
	Vector2 div(Vector2 rhs){
		new Vector2(
			this.x / rhs.x,
			this.y / rhs.y
		)
	}
	@Override
	Vector2 mod(Vector2 rhs){
		new Vector2(
			this.x % rhs.x,
			this.y % rhs.y
		)
	}

	@Override
	Vector2 next(){
		this.x++
		this.y++
		return this
	}
	@Override
	Vector2 previous(){
		this.x--
		this.y--
		return this
	}

	@Override
	Vector2 negative(){
		new Vector2(
			-this.x, 
			-this.y
		)
	}
	
	@Override
	Vector2 positive(){
		new Vector2(
			+this.x, 
			+this.y
		)
	}
	
	float getLength(){
		Math.sqrt(getLengthSq())
	}
	float getLengthSq(){
		this.x * this.x + this.y * this.y
	}
	Vector2 getNormalized(){
		float magnitude = getLength();
		new Vector2 (
			this.x / magnitude,
			this.y / magnitude
		)
	}
	float dotProduct(Vector2 from){
		from = from.normalized;
		Math.acos(this.x*from.x + this.y*from.y)

	}
	Vector2 truncate(float max){
		max = Math.abs(max)
		x = truncateOrKeep(x, max)
		y = truncateOrKeep(y, max)
		return this
	}
	float truncateOrKeep(float target, float max){
		if(target < 0){
			target = target < -max ? -max : target
		}else{
			target = target > max ? max : target
		}
		return target
	}
	float distance(Vector2 to){
		Vector2 space = this - to
		return space.length
	}
	/////// comparison
	/** note this equals does a straight and cross comparison, either X and X and Y and Y or X and Y and Y and X, because the way the compareto is handled*/
	@Override
	boolean equals(Object to){
		if(!to instanceof Vector2){
			return false
		}
		Vector2 target = (Vector2) to
		if(target.is(this)){
			return true
		}

		return compareStrategy.compare(this, target) == 0
		
	}
	@Override
	int compareTo(IVector2 to) {
		return compareStrategy.compare(this, to);
	}

	@Override
	boolean isCase(Vector2 lhs){
		return lhs == this
	}

	@Override
	int hashCode()
	{

		// use the sqrt of one field to differentiate between the two
		return (this.x * this.y).hashCode()
	}
}
