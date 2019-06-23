package Objects;

/**
 * abstract class (super class) that has only one abstract method to set the cosr for the two types of trucks:
 * 1-refrigeratedTruck
 * 2-ordinaryTruck
 * 
 * @author Kaish Kugashia
 */
public abstract class Truck {
	
	protected double cost = 0;
	public abstract void setCost(double input);
	
}
