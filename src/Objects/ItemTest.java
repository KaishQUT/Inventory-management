package Objects;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.*;
import Exceptions.*;

/**
 * Unit Tests for Class Item
 * @author Kaish Kugashia
 */

public class ItemTest {


	/**
	 * Initialize's the item which would read from the file and format the .csv data into a list of 'Items'.
	 * Throws IOException if the file is not open. Hence, all the Tests would fail. (as it is @Before).
	 */
	Item item;
	
	@Before
	public void init() throws IOException, CSVFormatException, StockException {
		item = null;
		Item.readItem("item_properties.csv");
	}

	/**
	 * Tests all the underlying methods of Item Class (Not testing the getList() 
	 * as it can be basically obtained by getItem(int i)).
	 */
	@Test
	public void AllItemPropertiesTest() {
		
		Item.items.get(9).setQuantity(100);
		Item.items.get(9).setReorderAmount(500);
		
		String Name = Item.items.get(9).getName();
		int Quantity = Item.items.get(9).getQuantity();
		double ManufacturingCost = Item.items.get(9).getManufacturingCost(); 
		double SellPrice = Item.items.get(9).getSellPrice(); 
		int ReorderPoint = Item.items.get(9).getReorderPoint();
		int ReorderAmount = Item.items.get(9).getReorderAmount();
		double Temperature = Item.items.get(9).getTemperature();
		String expected = Name+","+Quantity+","+ManufacturingCost+","+SellPrice+","+ReorderPoint+","+ReorderAmount+","+Temperature;
		String actual = Item.getItem(9);
		assertEquals(expected,actual);
	}

}
