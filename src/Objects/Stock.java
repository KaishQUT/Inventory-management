package Objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import Exceptions.*;

/**
 * The Stock Class deals with the weekly sales log
 * It reads the weekly sales log from a cvs file and
 * alters the inventory accordingly - reducing the item
 * quantities and increasing the store capital according 
 * to the selling price and sold quantities of the sold items. 
 * 
 * @author Kaish Kugashia
 */
public class Stock {
	
	public static HashMap<String, Integer> salesLogList = new HashMap<>();
	
	/**
	 * readSalesLog reads the csv file provided as the weekly sales log 
	 * and formats it.
	 * @param filename string that accept the CSV file name
	 * @throws IOException Input/Output files exceptions
	 * @throws StockException class extend exceptions that will catch the stock problems
	 * @throws DeliveryException class extend exceptions that will catch the delivery issues 
	 */
	public static void readSalesLog(String filename) throws IOException, DeliveryException, StockException {

		FileReader sales_log = new FileReader(filename);
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(sales_log);
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			String[] quantity_property = line.split(",");
			int sold_quantity = Integer.parseInt(quantity_property[1]);
			salesLogList.put(quantity_property[0], sold_quantity);
			
		}
		
		for (Item item : Item.items) {
			if (item.getQuantity() - salesLogList.get(item.getName()) >= 0) {
				item.setQuantity(item.getQuantity() - salesLogList.get(item.getName()));
			}
			else {
				throw new StockException();
			}
		}
		
		reader.close();	
		
		if (Item.items.isEmpty()) {
			throw new DeliveryException();
		}
		
	}
	
	/**
	 * getProfit Calculates the total capital generated from the weekly sales.
	 * i.e the product of the sold quantity and its corresponding selling price. 
	 * @return total capital obtained by sold items from the sales log.
	 */
	public static double getProfit() {
		double total = 0;
		if (!salesLogList.isEmpty()) {
			for (Item item : Item.items) {
				total += (item.getSellPrice() * salesLogList.get(item.getName()));
			}
		}
		return total;
	}
}
