package Objects;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import Exceptions.*;

/**
 * This is the Junit Test for class Store.
 * 
 * @author Kaish Kugashia 
 */
public class StoreTest {
	
	double testCapital = 1000.0; 
	
	/**
	 * This test checks iff there is only one instant of the
	 * Store at every instant.
	 */
	@Test
	public void testSingelton() {
		
		Store store = Store.getInstance();
		Store store1 =Store.getInstance();
		assertEquals(store1.hashCode(),store.hashCode());
	}
	
	/**
	 * Testing the method setCapital()
	 */
	@Test
	public void testSetCapital() {
		Store.setCapital(testCapital);
		testCapital += 100000;
		assertEquals(Store.getCapital(),testCapital,0.1);
	}
	
	/**
	 * Testing the method resetCapital()
	 * which is expected reset the capital to $100000. 
	 */ 
	@Test
	public void testResetCapital() {
		Store.resetCapital();
		assertEquals(Store.getCapital(),100000.0,0);
	}
	
	/**
	 * Testing the Name of the store.
	 */
	@Test
	public void testName() {
		assertEquals(Store.getName(),"SuperMart");
	}
	
	/**
	 * Testing inventory after first load manifest.
	 * @throws IOException Input/Output files exceptions
	 * @throws StockException class extend exceptions that will catch the stock problems
	 * @throws DeliveryException class extend exceptions that will catch the delivery issues 
	 * @throws CSVFormatException class extend exceptions that will catch the CSV format issues
	 */
	@Test
	public void testInventory() throws IOException, DeliveryException, StockException, CSVFormatException {
		Item.readItem("item_properties.csv");
		Item.items.get(1).setQuantity(100);
		double actual = Store.getInventory();
		assertEquals(actual,100.0,0);
	}
	
	/**
	 * Testing the resetInventory()
	 * we expect to reset the inventory value to 0
	 * every time we call resetInventory().
	 */
	@Test
	public void testResetInventory() {
		Store.resetInventory();
		assertEquals(Store.Inventory,0);
	}
	

}
