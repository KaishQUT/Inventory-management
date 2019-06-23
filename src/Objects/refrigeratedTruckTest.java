package Objects;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author Omar Alqarni - n9976353!qut.edu.au
 * 
 * This is a Test for the refrigeratedTruck class which basically test the method getCost.
 *
 */
public class refrigeratedTruckTest {
	
	/**
	 * Initialize's the ordinaryTruck object and a variable testQuantity to test the class.
	 */
	refrigeratedTruck r;
	double  testminTemp = -10;
	
	/**
	 * Test the cost method
	 */
	@Test
	public void testrefirgeretedTruck() {
		r = new refrigeratedTruck();
		double expected = 900.0 + (200.0 * Math.pow(0.7, testminTemp/5.0));
		r.setCost(testminTemp);
		double actual = r.getCost();
		assertEquals(expected,actual,0.1);
	}

}
