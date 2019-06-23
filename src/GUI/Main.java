package GUI;

import java.io.IOException;
import javax.swing.*;
import Exceptions.*;

/**
 * this is the GUI class. every time the class will run, a new GUI will be created and start the application.
 * The load button will basically load all the item properties.
 * 
 * @author Omar Alqarni - n9976353@qut.edu.au
 */
public class Main {
	
	/**
	 * 
	 * @param args the argument passed in main
	 * @throws IOException Input/Output files exceptions
	 * @throws StockException class extend exceptions that will catch the stock problems
	 * @throws DeliveryException class extend exceptions that will catch the delivery issues 
	 * @throws CSVFormatException class extend exceptions that will catch the CSV format issues
	 */
	public static void main(String[] args) throws IOException, StockException, DeliveryException, CSVFormatException {
		GUI gui = new GUI("SuperMart");
		GUI.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(gui);
	}

}
