package Exceptions;

import javax.swing.*;

/**
 * This is the CSV format exception handling. It has three different messages that will be thrown in the suitable situations
 * 
 * @author Omar Alqarni - n9976353@qut.edu.au
 */
@SuppressWarnings("serial")
public class CSVFormatException extends Exception {
	public static void ErrorMessage1() {
		JOptionPane.showMessageDialog(null,"Please provide a valid CSV file",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
	public static void ErrorMessage2() {
		JOptionPane.showMessageDialog(null,"Items already have been loaded",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
	public static void ErrorMessage3() {
		JOptionPane.showMessageDialog(null,"Manifest.csv has been created!",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
	public static void ErrorMessege4() {
		JOptionPane.showMessageDialog(null,"Nothing to reset!",
				"Error",JOptionPane.ERROR_MESSAGE); 
	}
}
