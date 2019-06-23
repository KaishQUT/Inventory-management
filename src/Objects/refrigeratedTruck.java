package Objects;

/**
 * Calculating the cost of one refrigerated truck based on the
 * minimum temperature it requires - based on the items it is 
 * moving.
 *
 * @author Kaish Kugashia 
 */
public class refrigeratedTruck extends Truck {
	
	/**
	 * set the cost of the refrigerated truck based on the minimum temperature between all the items
	 * @param minTemp calculate the cost based on the minimum temperature of items in the truck
	 */
	@Override
	public void setCost(double minTemp) {
		this.cost = 900.0 + (200.0 * Math.pow(0.7, minTemp/5.0));
	}
	
	/**
	 * getter for the cost
	 * @return cost cost of the truck
	 */
	public double getCost() {
		return cost;
	}

}
