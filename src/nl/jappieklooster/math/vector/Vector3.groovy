package nl.jappieklooster.math.vector

import nl.jappieklooster.math.vector.compareStrategies.ThreeWayComparator

class Vector3 implements IVector3{
	float z
	Vector2 base

	Vector3(){
		this(Vector2.defaultValue)
	}
	/** copy constructor, because handy*/
	Vector3(Vector3 input){
		this(input.x, input.y, input.z)
	}
	Vector3(double both){
		this((float) both)
	}
	Vector3(float both){
		this(both, both, both)
	}
	Vector3(double x, double y){
		this((float)x, (float)y, 0)
	}
	Vector3(double x, double y, double z){
		this((float) x, (float) y, (float) z)
	}
	Vector3(float x, float y, float z){
		base = new Vector2(x, y);
		compareStrategy = new ThreeWayComparator()
		this.z = z;
	}
	/**
	 * good for comparison but bad for calculations
	 * @return
	 */
	static Vector3 CreatePositiveInfinite(){
		return new Vector3(Float.POSITIVE_INFINITY)	
	}
	/**
	 * good for comparison but bad for calculations
	 * @return
	 */
	static Vector3 CreateNegativeInfinite(){
		return new Vector3(Float.NEGATIVE_INFINITY)	
	}
	/**
	 * works with calculation, but beware of overflow
	 * @return
	 */
	static Vector3 CreateBiggestFloat(){
		return new Vector3(Float.MAX_VALUE)
	}
	float getX(){
		base.x
	}
	float getY(){
		base.y
	}
	void setX(float value){
		base.x = value
	}
	void setY(float value){
		base.y = value
	}
	Comparator<IVector2> getCompareStrategy(){
		return base.compareStrategy
	}
	void setCompareStrategy(Comparator<IVector2> to){
		base.compareStrategy = to
	}
	@Override
	Object clone(){
		// this class only contains primitives, so changing anything is unecisary, as specified in documentation
		return super.clone();
	}

	@Override
	String toString(){
		return "(" + x + "," + y + "," + z +")"
	}
	///////// aritmatics
	Vector3 plus(Vector3 rhs){
		new Vector3(
			this.x + rhs.x,
			this.y + rhs.y,
			this.z + rhs.z
		)
	}
	Vector3 minus(Vector3 rhs){
		new Vector3(
			this.x - rhs.x,
			this.y - rhs.y,
			this.z - rhs.z
		)
	}
	Vector3 multiply(Vector3 rhs){
		new Vector3(
			this.x * rhs.x,
			this.y * rhs.y,
			this.z * rhs.z
		)
	}
	Vector3 power(Vector3 rhs){
		new Vector3(
			this.x ** rhs.x,
			this.y ** rhs.y,
			this.z ** rhs.z
		)
	}
	Vector3 div(Vector3 rhs){
		new Vector3(
			this.x / rhs.x,
			this.y / rhs.y,
			this.z / rhs.z
		)
	}
	Vector3 mod(Vector3 rhs){
		new Vector3(
			this.x % rhs.x,
			this.y % rhs.y,
			this.z % rhs.z
		)
	}

	Vector3 next(){
		this.x++
		this.y++
		this.z++
		return this
	}
	Vector3 previous(){
		this.x--
		this.y--
		this.z--
		return this
	}

	Vector3 negative(){
		new Vector3(
			-this.x, 
			-this.y,
			-this.z
		)
	}
	
	Vector3 positive(){
		new Vector3(
			+this.x, 
			+this.y,
			+this.z
		)
	}
	float getLength(){
		Math.sqrt(getLengthSq())
	}
	@Override
	float getLengthSq(){
		float xy = base.length
		xy*xy + z*z
	}
	Vector3 getNormalized(){
		float magnitude = getLength();
		new Vector3 (
			this.x / magnitude,
			this.y / magnitude,
			this.z / magnitude
		)
	}
	float dotProduct(Vector3 from){
		from = from.normalized
		Math.acos(this.x * from.x + this.y * from.y + this.z * from.z)
	}
	Vector3 truncate(float max){
		max = Math.abs(max)
		base.truncate(max)
		z = base.truncateOrKeep(z, max)
		return this

	}
	float distance(Vector2 to){
		Vector2 space = this - to
		return space.length
	}
	/////// comparison
	/** note this equals does a straight and cross comparison, either X and X and Y and Y or X and Y and Y and X, because the way the compareto is handled*/
	@Override
	boolean equals(Object to){
		if(to.is(this)){
			return true
		}
		if(!(to instanceof Vector3)){
			return false
		}
		Vector3 target = (Vector3) to

		return compareStrategy.compare(this, target) == 0
		
	}
	@Override
	int compareTo(IVector2 to) {
		return compareStrategy.compare(this, to);
	}

	boolean isCase(Vector3 lhs){
		return lhs == this
	}

	@Override
	int hashCode()
	{

		// use the sqrt of one field to differentiate between the two
		return (base.hashCode() * this.z).hashCode()
	}
	
	static Vector3 max(){
		new Vector3(Float.MAX_VALUE)
	}

	float getAt(int index){
		if(index == Dimension.z.value()){
			return z
		}
		return base[index]
	}
	
	void putAt(int index, float value) {
		if(index == Dimension.z.value()){
			z = value
			return
		}
		base[index] = value
	}
	
	/**
	 * if the input has any lower values then the input it will be assigned to this
	 * usefull together with max for creating bounding boxes
	 * @param input
	 */
	void assimilateMin(Vector3 input){
		if(input.x < x){
			x = input.x
		}
		if(input.y < y){
			y = input.y
		}
		if(input.z < z){
			z = input.z
		}
	}
	void assimilateMax(Vector3 input){
		if(input.x > base.x){
			base.x = input.x
		}
		if(input.y > base.y){
			base.y = input.y
		}
		if(input.z > z){
			z = input.z
		}
	}
	
}
