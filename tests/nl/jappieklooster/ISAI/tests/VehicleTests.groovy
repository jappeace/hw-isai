package nl.jappieklooster.ISAI.tests;

import static org.junit.Assert.*;

import nl.jappieklooster.ISAI.entity.impl.Vehicle
import nl.jappieklooster.math.vector.Vector3
import org.junit.Before;
import org.junit.Test;

class VehicleTests {

	Vehicle testVehicle
	@Before
	public void setUp() throws Exception {
		testVehicle = new Vehicle()
		testVehicle.position = new Vector3(3,2,-50)
		testVehicle.heading = new Vector3(1,0,0)
	}

	@Test
	public void testlocalToWorld() {
		Vector3 input = new Vector3(3,1,20)
		Vector3 expected = new Vector3(6,2,-50)
		assertEquals(expected,testVehicle.localToWorld(input))
	}


}
