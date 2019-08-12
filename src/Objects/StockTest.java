package Objects;

import Exceptions.CSVFormatException;
import Exceptions.DeliveryException;
import Exceptions.StockException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * This is a test class using JUnit 4 for testing the Stock class 
 * 
 * @author Omar Alqarni - n9976353@qut.edu.au
 */
public class StockTest {
	
	/**
	 * This will read all the items first from Item class. Also, exceptions are used since we are using them in 
	 * Item.readItem method.
	 * 
	 * @throws IOException Input/Output files exceptions
	 * @throws CSVFormatException class extend exceptions that will catch the CSV format issues
	 * @throws StockException class extend exceptions that will catch the stock problems
	 */
	@Before
	public void init() throws IOException, CSVFormatException, StockException {
		Item.readItem("item_properties.csv");
		Item.items.get(0).setQuantity(600);
	}
	
	/**
	 * Test the quantity after loading the sales log. This will test the quantity that should be changed 
	 * when running the Stock class.
	 * 
	 * @throws IOException Input/Output files exceptions
	 * @throws DeliveryException class extend exceptions that will catch the delivery issues 
	 * @throws StockException class extend exceptions that will catch the stock problems
	 */
	@Test
	public void readSalesLogTest() throws IOException, StockException, DeliveryException {
		try {
			Stock.readSalesLog("sales_log_0.csv");
		} catch (StockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(512, Item.items.get(0).getQuantity());
	}

}
