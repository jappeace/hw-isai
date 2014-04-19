package nl.jappieklooster.math.vector

class Vector3 extends Vector2{
	float z

	Vector3(){
		this(defaultValue,defaultValue)
	}
	/** copy constructor, because handy*/
	Vector3(Vector3 input){
		this(input.x, input.y, input.z)
	}
	Vector3(double both){
		this((float) both)
	}
	Vector3(float both){
		this(both, both)
	}
	Vector3(double x, double y){
		this((float)x, (float)y)
	}
	Vector3(float x, float y, float z){
		super(x, y);
		this.z = z;
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
	@Override
	Vector3 plus(Vector3 rhs){
		new Vector3(
			this.x + rhs.x,
			this.y + rhs.y,
			this.z + rhs.z
		)
	}
	@Override
	Vector3 minus(Vector3 rhs){
		new Vector3(
			this.x - rhs.x,
			this.y - rhs.y,
			this.z - rhs.z
		)
	}
	@Override
	Vector3 multiply(Vector3 rhs){
		new Vector3(
			this.x * rhs.x,
			this.y * rhs.y,
			this.z * rhs.z
		)
	}
	@Override
	Vector3 power(Vector3 rhs){
		new Vector3(
			this.x ** rhs.x,
			this.y ** rhs.y,
			this.z ** rhs.z
		)
	}
	@Override
	Vector3 div(Vector3 rhs){
		new Vector3(
			this.x / rhs.x,
			this.y / rhs.y,
			this.z / rhs.z
		)
	}
	@Override
	Vector3 mod(Vector3 rhs){
		new Vector3(
			this.x % rhs.x,
			this.y % rhs.y,
			this.z % rhs.z
		)
	}

	@Override
	Vector3 next(){
		this.x++
		this.y++
		this.z++
		return this
	}
	@Override
	Vector3 previous(){
		this.x--
		this.y--
		this.z--
		return this
	}

	@Override
	Vector3 negative(){
		new Vector3(
			-this.x, 
			-this.y,
			-this.z
		)
	}
	
	@Override
	Vector3 positive(){
		new Vector3(
			+this.x, 
			+this.y,
			+this.z
		)
	}
	/////// comparison
	/** note this equals does a straight and cross comparison, either X and X and Y and Y or X and Y and Y and X, because the way the compareto is handled*/
	@Override
	boolean equals(Object to){
		if(!to instanceof Vector3){
			return false
		}
		Vector3 target = (Vector3) to
		if(target.is(this)){
			return true
		}

		return compareStrategy.compare(this, target) == 0
		
	}
	@Override
	int compareTo(Vector3 to) {
		return compareStrategy.compare(this, to);
	}

	@Override
	boolean isCase(Vector3 lhs){
		return lhs == this
	}

	@Override
	int hashCode()
	{

		// use the sqrt of one field to differentiate between the two
		return (super.hashCode() * this.z).hashCode()
	}
}
