package Objects;

/**
 * Calculating the cost of one ordinary truck based on the
 * number of items it is moving.
 *
 * @author Kaish Kugashia
 */
public class ordinaryTruck extends Truck {
	
	/**
	 * set the cost of the ordinary truck based on the total quantities of all the items
	 * @param totalQuantityInTruck calculate the cost based on the quantity of items in the truck
	 */
	@Override
	public void setCost(double totalQuantityInTruck) {
		this.cost = 750.0 + (0.25 * totalQuantityInTruck);
	}
	
	/**
	 * getter for the cost
	 * @return cost cost of the truck
	 */
	public double getCost() {
		return cost;
	}

}
