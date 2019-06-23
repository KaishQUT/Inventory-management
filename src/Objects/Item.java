package Objects;

import java.io.*;
import java.util.*;
import Exceptions.*;

/**
 * This class will read the item properties from item_properties.csv file. In each row, an object of the Item 
 * class will be created. This class have one constructor with a temperature property. For each property, 
 * a getter was created and used to return the properties of each item. getList method will get all the items stored.
 * getItem will get a specific item passing a specific index. Finally, a method that will return an object[] to add 
 * them to the JTable.
 * 
 * @author Omar Alqarni - n9976353@qut.edu.au
 */
public class Item {

	private String Name;
	private double ManufacturingCost;
	private double SellPrice;
	private int ReorderPoint;
	private int ReorderAmount;
	private double Temperature;
	private int quantity = 0;
	private int amountLeftInManifest;
	public static List<Item> items = new ArrayList<>();
	static List<String> itemAlreadyIn = new ArrayList<>();
	private static boolean itemsHaveLoaded = false;

	/**
	 * This method will read the csv file properties and create an object for each item. If the item doesn't require a temperature,
	 * the field will be set to 50 degree.
	 * 
	 * @param filename string that accept the CSV file name 
	 * @throws IOException Input/Output files exceptions
	 * @throws StockException class extend exceptions that will catch the stock problems
	 * @throws CSVFormatException class extend exceptions that will catch the CSV format issues
	 */
	public static void readItem(String filename) throws IOException, CSVFormatException, StockException {
		
		if (itemsHaveLoaded && !items.isEmpty()) {
			throw new CSVFormatException();
		}
		itemsHaveLoaded = true;
		items.clear();
		itemAlreadyIn.clear();
		
		FileReader item_properties = new FileReader(filename);
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(item_properties);
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			String[] properties = line.split(",");
			
			double ManufacturingCost = Double.parseDouble(properties[1]);
			double SellPrice = Double.parseDouble(properties[2]);
			int ReorderPoint = Integer.parseInt(properties[3]);
			int ReorderAmount = Integer.parseInt(properties[4]);
			
			if (itemAlreadyIn.contains(properties[0])) {
				itemsHaveLoaded = false;
				throw new StockException();
			}
			itemAlreadyIn.add(properties[0]);
			
			if (properties.length > 5) {
				double Temperature = Double.parseDouble(properties[5]);
				Item item = new Item(properties[0], ManufacturingCost, SellPrice, ReorderPoint, ReorderAmount, ReorderAmount, 0, Temperature);
				items.add(item);
			}
			else if (properties.length <= 5) {
				int Temperature = 50;
				Item item = new Item(properties[0], ManufacturingCost, SellPrice, ReorderPoint, ReorderAmount, ReorderAmount, 0, Temperature);
				items.add(item);
			}
		}
		reader.close();
		
	}
	
	/**
	 * The constructor for Item Class that has all the properties of an item.
	 * @param Name the name of the item
	 * @param ManufacturingCost the manufacturing cost of the item in dollars
	 * @param SellPrice the sell price of the item in dollars
	 * @param ReorderPoint the reorder point to specify whether the item need to be ordered or not
	 * @param ReorderAmount the amount that should be reordered
	 * @param amountLeftInManifest amount of each item left in the manifest to sort them in trucks
	 * @param quantity the current quantity of each item
	 * @param Temperature the temperature required in truck of each item, it will be 50 if the item doeasn't require temperature
	 */
	public Item(String Name, double ManufacturingCost, double SellPrice, int ReorderPoint, int ReorderAmount, int amountLeftInManifest, int quantity, double Temperature) {
		this.Name = Name;
		this.ManufacturingCost = ManufacturingCost;
		this.SellPrice = SellPrice;
		this.ReorderPoint = ReorderPoint;
		this.ReorderAmount = ReorderAmount;
		this.amountLeftInManifest = amountLeftInManifest;
		this.Temperature = Temperature;
		this.quantity = quantity;
	}
	
	/**
	 * getter for the name property
	 * @return Name name of the item
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * getter for the manufacturing cost property
	 * @return ManufacturingCost Manufacturing cost of each item
	 */
	public double getManufacturingCost() {
		return ManufacturingCost;
	}
	
	/**
	 * getter for the sell price property
	 * @return SellPrice the sell price of each item
	 */
	public double getSellPrice() {
		return SellPrice;
	}
	
	/**
	 * getter for the reorder point property
	 * @return ReorderPoint the reorder point of the item
	 */
	public int getReorderPoint() {
		return ReorderPoint;
	}
	
	/**
	 * getter for the reorder amount
	 * @return ReorderAmount the reorder amount of the item
	 */
	public int getReorderAmount() {
		return ReorderAmount;
	}
	
	/**
	 * getter for the reorder amount used in the GUI
	 * @return amountLeftInManifest the amount that is still not sorted in any truck 
	 */
	public int getAmountLeftInManifest() {
		return amountLeftInManifest;
	}
	
	/**
	 * getter for the quantity
	 * @return quantity the quantity of the item
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * getter for the temperature property
	 * @return Temperature the temperature of the item
	 */
	public double getTemperature() {
		return Temperature;
	}
	
	/**
	 * setter for reorderAmoint field
	 * @param ReorderAmount set the reorder amount of the item
	 */
	public void setReorderAmount(int ReorderAmount) {	
		this.ReorderAmount = ReorderAmount;
	}
	/**
	 * Setter for the reorder amount in the manifest list
	 * @param amountLeftInManifest set the amount left in manifest of the item
	 */
	public void setAmountLeftInManifest(int amountLeftInManifest) {
		this.amountLeftInManifest = amountLeftInManifest;
	}
	/**
	 * setter for the quantity
	 * @param quantity set the quantity of the item
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * This method will get the properties of a specific item.
	 * @param i index to get the item from the arrayList items
	 * @return itemProperties all the properties of the item
	 */
	public static String getItem(int i) {
			return Item.items.get(i).getName() + "," + Item.items.get(i).getQuantity() + "," + 
				   Item.items.get(i).getManufacturingCost() + "," + Item.items.get(i).getSellPrice() + "," + 
				   Item.items.get(i).getReorderPoint() + "," + Item.items.get(i).getReorderAmount() + "," + 
				   Item.items.get(i).getTemperature();
	}
	
	/**
	 * This method will return each item in an object array and will be used in JTable, "N/A" will be passed
	 * to the items that do not require temperature
	 * @return item_properties all the properties of the item
	 */
	public Object[] getArray() {
		if (Temperature == 50) {
			String Temperature = "N/A";
			Object[] item_properties = {Name, quantity, String.format("$%,.2f", ManufacturingCost), 
					String.format("$%,.2f", SellPrice), ReorderPoint, ReorderAmount, Temperature};
			return item_properties;
		}
		else {
			Object[] item_properties = {Name, quantity, String.format("$%,.2f", ManufacturingCost), 
					String.format("$%,.2f", SellPrice), ReorderPoint, ReorderAmount, Temperature};
			return item_properties;
		}
	}
	
}