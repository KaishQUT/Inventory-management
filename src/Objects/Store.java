package Objects;

/**
 * This is a Store class that has been created using the Singleton pattern, which basically will prevent the 
 * programmer from initializing more than one object of the store. The store will have initially a capital of 100000..
 * 
 * @author Omar Alqarni - n9976353@qut.edu.au
 */
public class Store {
	
	private static Store store = null;
	private static double Capital = 100000.0;
	private static double resetCapital = 100000.0;
	private static String Name = "SuperMart";
	public static int Inventory;
	private static int resetInventory = 0;

	private Store() {
		
	}
	
	/**
	 * this method will prevent the programmer from initialize the Store class twice
	 * @return store object of the store
	 */
	public static Store getInstance() {
		
		if (store == null) {
			store = new Store();
		}

		return store;
	}
	
	/**
	 * get the capital of the store
	 * @return Capital the current capital amount
	 */
	public static double getCapital() {
		return Capital ;
	}
	/**
	 * set the capital of the store
	 * @param capital add this parameter to the current capital 
	 */
	public static void setCapital(double capital) {
		Store.Capital += capital;
	}
	/**
	 * get the name of the store
	 * @return Name name of the store
	 */
	public static String getName() {
		return Store.Name;
	}
	/**
	 * get the inventory name of the store
	 * @return Inventory total quantity in the inventory
	 */
	public static int getInventory() {
		Inventory = 0;
		for (Item item : Item.items) {
			Inventory += item.getQuantity();
		}
		return Store.Inventory;
	}
	/**
	 * method that will reset the capital amount
	 */
	public static void resetCapital() {
		Capital = resetCapital;
	}
	/**
	 * method that will reset the inventory amount
	 */
	public static void resetInventory() {
		Inventory = resetInventory;
	}
	
	
}
