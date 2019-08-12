package Exceptions;

import javax.swing.*;

/**
 * This is the Delivery exception handling for dealing with creating the manifest or reading the sales log while there is no item. 
 * It has two different messages that will be thrown in the suitable situations.
 * 
 * @author Omar Alqarni
 */
@SuppressWarnings("serial")
public class DeliveryException extends Exception {
	public static void ErrorMessage1() {
		JOptionPane.showMessageDialog(null,"No items provided",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
	public static void ErrorMessage2() {
		JOptionPane.showMessageDialog(null,"Create manifest file first",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
	
}
