package Exceptions;

import javax.swing.JOptionPane;

/**
 * This is the Stock exception handling for dealing with loading the sales log. This will show an error message when there 
 * is no enough quantity for at least one product. It has only one message that will be thrown in the suitable situations.
 * 
 * @author Omar Alqarni - n9976353@qut.edu.au
 */
@SuppressWarnings("serial")
public class StockException extends Exception {
	public static void ErrorMessage1() {
		JOptionPane.showMessageDialog(null,"No enough quantity to sell for at least one product",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
	public static void ErrorMessage2() {
		JOptionPane.showMessageDialog(null,"remove duplicate elements!",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
	public static void ErrorMessage3() {
		JOptionPane.showMessageDialog(null,"No elements under Reorder Point!",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
}
