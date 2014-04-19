package nl.jappieklooster.math.vector

import nl.jappieklooster.math.vector.compareStrategies.TwoWayComparator

/**
 * this is a basic 2 double container with all the operator overloading that made sense, so no getat and putat but practicly evreything else
 * I chose doubles, because java is going to be slow anyways, so Might aswell store a bunch of data.
 * Besides this class is very handy in cartesian situations, which is considerd a 2D space, which gives the 2D a double meaning...
 * @author jappie
 *
 */
interface IVector2 extends Cloneable, Comparable<IVector2>{
	float getX()
	float getY()
	void setX(float value)
	void setY(float value)
}
