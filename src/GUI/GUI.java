package GUI;

import Exceptions.CSVFormatException;
import Exceptions.DeliveryException;
import Exceptions.StockException;
import Objects.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * This will build the GUI content each time the main will be run. Different components were used for the project.
 * The basic panel will have other sub-panels and boxes that contains the buttons, textfields, labels and the jTable.
 * 
 * @author Omar Alqarni - n9976353@qut.edu.au
 */
public class GUI extends JFrame implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	public static boolean manifestFlag = false;
	// The basic panel
	JPanel panel = new JPanel();
	// Table
	JPanel TablePanel = new JPanel();
	String[] col = { "Name", "Quantity", "Cost ($)", "Price ($)", "Reorder point", "Reorder quantity", "Temperature (C)"};
	DefaultTableModel tableModel = new DefaultTableModel(col, 0);
	JTable table = new JTable(tableModel);
	// Buttons
	Box buttonsBox = Box.createVerticalBox();
	JButton LoadItemsButton = new JButton("   Load Items    ");
	JButton SalesLogButton = new JButton("Load Sales Log ");
	JButton createManifestButton = new JButton("Create Manifest");
	JButton loadManifestButton = new JButton(" Load Manifest  ");
	JButton reset = new JButton("      Reset all      ");
	// TextFields
	Box TextBox = Box.createVerticalBox();
	// Labels and uneditable text fields
	JLabel storeNameLabel = new JLabel("   Store Name:");
	JLabel storeCapitalLabel = new JLabel("   Capital:");
	JLabel storeInventoryLabel = new JLabel("   Inventory (total quantities):");
	JLabel storeNameTxtField = new JLabel(Store.getName());
	refrigeratedTruck refTruck = new refrigeratedTruck();
	JTextField storeCapitalTxtField = new JTextField(String.format("$%,.2f", Store.getCapital()));
	JLabel storeInventoryTxtField = new JLabel(String.format("%d", Store.getInventory()));
	
	/**
	 * This method will initialize the GUI form and put all the initialized components on the form.
	 * Moreover, the actionlistener and the exceptions handling are detected in this method by catching the created
	 * classes for the exceptions
	 */
	public void createGUI() {
		
		this.setSize(900, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new BorderLayout());
		this.add(panel);
		
		// table
		table.setPreferredScrollableViewportSize(new Dimension(600, 250));
		table.setFillsViewportHeight(true);
		TablePanel.add(new JScrollPane(table));
		table.setEnabled(false);
		
		// buttons
		buttonsBox.add(Box.createVerticalStrut(4));
		buttonsBox.add(LoadItemsButton);
		buttonsBox.add(SalesLogButton);
		buttonsBox.add(Box.createVerticalStrut(10));
		buttonsBox.add(createManifestButton);
		buttonsBox.add(loadManifestButton);
		buttonsBox.add(reset);
		buttonsBox.add(Box.createVerticalStrut(40));
		buttonsBox.add(storeNameLabel);
		buttonsBox.add(Box.createVerticalStrut(4));
		buttonsBox.add(storeCapitalLabel);
		buttonsBox.add(Box.createVerticalStrut(4));
		buttonsBox.add(storeInventoryLabel);

		// Listener for Item properties (LoadItemsButton)
		LoadItemsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String filename = readCSV();
					Item.readItem(filename);
					updateTable();
					storeInventoryTxtField.setText(String.format("%d", Store.getInventory()));
				} catch (IOException e1) {
					updateTable();
					JOptionPane.showMessageDialog(null,"Please enter a valid item properties csv filename",
							"Error",JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException e2) {
					updateTable();
					CSVFormatException.ErrorMessage1();
				} catch (CSVFormatException e3) {
					updateTable();
					CSVFormatException.ErrorMessage2();
				} catch (StockException e4) {
					StockException.ErrorMessage2();
				} catch (Exception e5) {
					updateTable();
					CSVFormatException.ErrorMessage1();
				}
			}	
		});
		
		// Listener for Sales Log (SalesLogButton)
		SalesLogButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String filename = readCSV();
					Stock.readSalesLog(filename);
					updateTable();
					manifestFlag = false;
					Store.setCapital(Stock.getProfit());
					storeCapitalTxtField.setText(String.format("$%,.2f", Store.getCapital()));
					storeInventoryTxtField.setText(String.format("%d", Store.getInventory()));
					JOptionPane.showMessageDialog(null, "Weekly sales log have been subtracted from quantities", "Sales Log", getDefaultCloseOperation());
				} catch (IOException e1) {
					updateTable();
					JOptionPane.showMessageDialog(null,"Please enter a valid sales log csv filename",
							"Error",JOptionPane.ERROR_MESSAGE);
					table.updateUI();
				}  catch (NumberFormatException e3) {
					updateTable();
					CSVFormatException.ErrorMessage1();
				} catch (NullPointerException e4) {
					updateTable();
					CSVFormatException.ErrorMessage1();
				} catch (ArrayIndexOutOfBoundsException e5) {
					updateTable();
					CSVFormatException.ErrorMessage1();
				} catch (StockException e6) {
					updateTable();
					StockException.ErrorMessage1();
				} catch (DeliveryException e7) {
					updateTable();
					DeliveryException.ErrorMessage1();
				}
			}	
		});
		
		// Listener for Manifest (ManifestButton)
		createManifestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Manifest.createManifest();
					updateTable();
					manifestFlag = true;
					JOptionPane.showMessageDialog(null, "manifest.csv file has been created", "Manifest", getDefaultCloseOperation());
				} catch (IOException e1) {
					updateTable();
					DeliveryException.ErrorMessage1();
				} catch (NoSuchElementException e2) {
					updateTable();
					DeliveryException.ErrorMessage1();
				} catch (IndexOutOfBoundsException e3) {
					updateTable();
					DeliveryException.ErrorMessage1();
				} catch (DeliveryException e4) {
					updateTable();
					DeliveryException.ErrorMessage1();
				} catch (StockException e5) {
					updateTable();
					StockException.ErrorMessage3();
				} catch (CSVFormatException e6) {
					updateTable();
					CSVFormatException.ErrorMessage3();
				}
			}	
		});
		
		// Listener for Load Manifest (loadManifestButton)
		loadManifestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Manifest.loadManifest();
					updateTable();
					manifestFlag = false;
					Store.setCapital(-Manifest.totalCost);
					storeCapitalTxtField.setText(String.format("$%,.2f", Store.getCapital()));
					storeInventoryTxtField.setText(String.format("%d", Store.getInventory()));
					JOptionPane.showMessageDialog(null, "manifest has been loaded to quantity", "Manifest", getDefaultCloseOperation());
				} catch (IOException e1) {
					updateTable();
					DeliveryException.ErrorMessage2();
				} catch (NoSuchElementException e2) {
					updateTable();
					DeliveryException.ErrorMessage2();
				} catch (IndexOutOfBoundsException e3) {
					updateTable();
					DeliveryException.ErrorMessage2();
				} catch (DeliveryException e4) {
					updateTable();
					DeliveryException.ErrorMessage2();
				}
			}	
		});

		// Listener for Manifest (ManifestButton)
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Item.items.isEmpty() && Store.getCapital() == 100000.0 && tableModel.getRowCount() == 0) {
					try {
						throw new CSVFormatException();
					} catch (CSVFormatException e1) {
						// TODO Auto-generated catch block
						CSVFormatException.ErrorMessege4();
					}
				}
				else {
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to reset all?");
					if (dialogResult == JOptionPane.YES_OPTION){
						Item.items.clear();
						Store.resetCapital();
						Store.resetInventory();
						storeCapitalTxtField.setText(String.format("$%,.2f", Store.getCapital()));
						storeInventoryTxtField.setText(String.format("%d", Store.getInventory()));
						tableModel.setRowCount(0);
					}
				}
		}});
		
		// text fields
		storeCapitalTxtField.setMaximumSize(new Dimension(200, 25));
		TextBox.add(Box.createVerticalStrut(199));
		TextBox.add(storeNameTxtField);
		storeCapitalTxtField.setEditable(false);
		TextBox.add(storeCapitalTxtField);
		TextBox.add(storeInventoryTxtField);
		
		panel.add(buttonsBox, BorderLayout.WEST);
		panel.add(TablePanel, BorderLayout.EAST);
		panel.add(TextBox, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public String readCSV() {
		File workingDirectory = new File(System.getProperty("user.dir"));
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setCurrentDirectory(workingDirectory);
		jfc.setDialogTitle("Select a csv file");
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
		jfc.addChoosableFileFilter(filter);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			return selectedFile.getAbsolutePath();
		}
		return null;
	}
	
	/**
	 * This method will update the table each time it will be called
	 */
	public void updateTable() {
		tableModel.setRowCount(0);
		for (Item item : Item.items) {
			Object[] item_properties = item.getArray();
			tableModel.addRow(item_properties);
		}
	}
	
	public GUI(String title) throws HeadlessException {
		super(title);
	}

	@Override
	public void run() {
		createGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
}