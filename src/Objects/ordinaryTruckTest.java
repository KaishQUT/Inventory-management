package Objects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a Test for the ordinaryTruck class which basically test the method getCost.
 * 
 * @author Omar Alqarni - n9976353!qut.edu.au
 */
public class ordinaryTruckTest {
		
		/**
		 * Initialize's the ordinaryTruck object and a variable testQuantity to test the class.
		 */
		ordinaryTruck o;
		double testQuantity = 1200;
	
		
		/**
		 * Test the cost method
		 */
		@Test
		public void testOrdinaryTruck() {
			o = new ordinaryTruck();
			o.setCost(testQuantity);
			double expectedCost  = 1050.0;
			double actualCost = o.getCost();
			assertEquals(expectedCost,actualCost,0.1);
		}
}
