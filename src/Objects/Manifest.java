package Objects;

import java.io.*;
import java.util.*;
import Exceptions.*;
import GUI.*;

/**
 * This class Loads and creates manifest for the store.
 * The Manifest created are optimized to reduce the transportation 
 * cost. 
 * Creating a manifest would generate a list of trucks and items it will
 * contain. 
 * Loading a manifest would increase the inventory and decrease the store
 * capital. 
 * 
 * @author Kaish Kugashia
 */
public class Manifest {
	
	public static HashMap<String, Integer> loadedManifestList = new HashMap<>();
	public static List<Item> manifestList = new ArrayList<>();
	static List<Double> ItemTemp = new ArrayList<>();
	public static int totalQuantity = 0;
	private static int refTruckCapacity = 800;
	private static int ordTruckCapacity = 1000;
	static List<Double> minTempInTruck = new ArrayList<>();
	public static double totalCost;
	private static int totalQuantityInOrd;
	
	/**
	 * This method will load the manifest, Loading a manifest would increase
	 * the inventory and decrease the store
	 * capital.
	 * @throws IOException
	 * @throws DeliveryException
	 */
	public static void loadManifest() throws IOException, DeliveryException {
		
		if (!GUI.manifestFlag) {
			throw new DeliveryException();
		}
		
		loadedManifestList.clear();
		for (String item : loadedManifestList.keySet()) {
			loadedManifestList.put(item, 0);
		}
		
		FileReader readManifest = new FileReader("manifest.csv");
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(readManifest);
		
		while (true) {
			String line = reader.readLine();
			
			if (line == null) {
				break;
			}
			
			if (!line.matches(">.*")) {
				String[] truckElement = line.split(",");
			    String item = truckElement[0].trim();
			    int quantity = Integer.parseInt(truckElement[1].trim());
			    loadedManifestList.merge(item, quantity, Integer::sum);
			}
		}

		for (Item item : Item.items) {
			for (String itemInManifest : loadedManifestList.keySet()) {
				if (item.getName().equals(itemInManifest.toString())) {
					item.setQuantity(item.getQuantity() + item.getReorderAmount());
				}
			}
		}
	}
	
	/**
	 * Creating a manifest would generate a list of trucks and items it will
	 * contain.
	 * @throws IOException IOException Input/Output files exceptions
	 * @throws DeliveryException class extend exceptions that will catch the delivery issues 
	 * @throws StockException class extend exceptions that will catch the stock problems
	 * @throws CSVFormatException class extend exceptions that will catch the CSV format issues
	 */
	@SuppressWarnings("resource")
	public static void createManifest() throws IOException, DeliveryException, StockException, CSVFormatException {

		if (Item.items.isEmpty()) {
			throw new DeliveryException();
		}
		
		if (GUI.manifestFlag) {
			throw new CSVFormatException();
		}

		PrintWriter manifest = new PrintWriter(new FileWriter("manifest.csv", false));
		StringBuilder manifest_sb = new StringBuilder();
		totalCost = 0;
		for (Item item : Item.items) {
			if (item.getReorderPoint() > item.getQuantity()) {
				manifestList.add(item);
				ItemTemp.add(item.getTemperature());
				totalQuantity += item.getReorderAmount();
				totalCost += (item.getManufacturingCost()*item.getReorderAmount());
			}
		}
		sortList();
		optimizingSort();
		

		if (manifestList.isEmpty()) {
			throw new StockException();
		}

			int coldItem = getItemNeedTemp();
			int hotItem = totalQuantity - coldItem;

			if (totalQuantity <= refTruckCapacity) {

				manifest_sb.append(">Refrigerated ");
				manifest_sb.append("\n");
				refrigeratedTruck refTruck = new refrigeratedTruck();
				minTempInTruck.clear();
				for (Item item: manifestList) {
					manifest_sb.append(item.getName());
					manifest_sb.append(",");
					manifest_sb.append(item.getAmountLeftInManifest());
					manifest_sb.append("\n");
					minTempInTruck.add(item.getTemperature());
				}
				refTruck.setCost(minTempInTruck.get(minTempInTruck.indexOf(Collections.max(minTempInTruck))));
				totalCost += refTruck.getCost();
			}
			else {
				int flag = 0;
				int spaceCorrection = 0;

				for (int i = 0; i < coldItem/refTruckCapacity + 1; i++) {
					int spaceLeft = refTruckCapacity;
					int j = 0;

					manifest_sb.append(">Refrigerated " + (i+1));
					manifest_sb.append("\n");
					refrigeratedTruck refTruck = new refrigeratedTruck();
					minTempInTruck.clear();
					while (spaceLeft > 0 && manifestList.size() > 0) {
						minTempInTruck.add(manifestList.get(j).getTemperature());
						if (spaceLeft >= manifestList.get(j).getAmountLeftInManifest()) {
							spaceLeft -= manifestList.get(j).getAmountLeftInManifest();

							manifest_sb.append(manifestList.get(j).getName());
							manifest_sb.append(",");
							manifest_sb.append(manifestList.get(j).getAmountLeftInManifest());
							manifest_sb.append("\n");

							if (flag == 1) {
								manifestList.get(j).setAmountLeftInManifest(manifestList.get(j).getAmountLeftInManifest()+spaceCorrection);
								spaceCorrection = 0;
							}

							manifestList.remove(j);

						}
						else if (spaceLeft < manifestList.get(j).getAmountLeftInManifest()) {
							manifestList.get(j).setAmountLeftInManifest(manifestList.get(j).getAmountLeftInManifest()-spaceLeft);
							flag = 1;
							spaceCorrection = spaceLeft;
							manifest_sb.append(manifestList.get(j).getName());
							manifest_sb.append(",");
							manifest_sb.append(spaceLeft);
							manifest_sb.append("\n");
							spaceLeft = 0;
							break;
						}
					}
					refTruck.setCost(minTempInTruck.get(minTempInTruck.indexOf(Collections.min(minTempInTruck))));
					totalCost += refTruck.getCost();
				}
				int flag1 = 0;
				int spaceCorrection1 = 0;
				for (int l = 0; l < hotItem/ordTruckCapacity + 1; l++) {
					int spaceLeftOr = ordTruckCapacity;
					int k = 0;
					manifest_sb.append(">Ordinary " + (l+1));
					manifest_sb.append("\n");
					totalQuantityInOrd = 0;
					ordinaryTruck ordTruck = new ordinaryTruck();
					while (spaceLeftOr > 0 && manifestList.size() > 0) {
						if (spaceLeftOr >= manifestList.get(k).getAmountLeftInManifest()) {
							spaceLeftOr -= manifestList.get(k).getAmountLeftInManifest();

							manifest_sb.append(manifestList.get(k).getName());
							manifest_sb.append(",");
							manifest_sb.append(manifestList.get(k).getAmountLeftInManifest());
							manifest_sb.append("\n");
							totalQuantityInOrd += manifestList.get(k).getAmountLeftInManifest();

							if (flag1 == 1) {
								manifestList.get(k).setAmountLeftInManifest(manifestList.get(k).getAmountLeftInManifest()+spaceCorrection1);
								spaceCorrection1 = 0;
							}

							manifestList.remove(k);
						}
						else if (spaceLeftOr < manifestList.get(k).getAmountLeftInManifest()) {
							manifestList.get(k).setAmountLeftInManifest(manifestList.get(k).getAmountLeftInManifest()-spaceLeftOr);
							flag1 = 1;
							spaceCorrection1 = spaceLeftOr;
							manifest_sb.append(manifestList.get(k).getName());
							manifest_sb.append(",");
							manifest_sb.append(spaceLeftOr);
							manifest_sb.append("\n");

							totalQuantityInOrd += spaceLeftOr;
							spaceLeftOr = 0;
						}
					}
					if (totalQuantityInOrd != 0) {
						ordTruck.setCost(totalQuantityInOrd);
						totalCost += ordTruck.getCost();
					}
					if (totalQuantityInOrd == 0) {
						manifest_sb.delete(manifest_sb.lastIndexOf(">"), manifest_sb.length());
					}
				}		
			}

		manifest.write(manifest_sb.toString());
		manifest.close();
	}
	
	/**
	 * Total quantity of items that would require a refrigerated truck
	 * @return ItemNeedTemp
	 */
	public static int getItemNeedTemp() {	
		int ItemNeedTemp = 0;
		for (Item item : manifestList) {
			if (item.getTemperature() <= 10) {
				ItemNeedTemp += item.getAmountLeftInManifest();
			}
		}
		return ItemNeedTemp;
	}
	
	/**
	 * Number of Items that need refrigerated truck
	 * @return ItemNeedTemp the number of the items that need temperature
	 */
	public static int getNumberOfItemNeedTemp() {	
		int ItemNeedTemp = 0;
		for (Item item : manifestList) {
			if (item.getTemperature() <= 10) {
				ItemNeedTemp++;
			}
		}
		return ItemNeedTemp;
	}
	
	/**
	 * Sorts the list in the order: Cold - Hot
	 * Sorting is just dividing the cold from hot items.
	 */
	public static void sortList() {
		int i = 0;
		int j = manifestList.size()-1;
		
		while (i <= j) {
			
			if (manifestList.get(i).getTemperature() < 11) {
				i++;
			}
			else {
				swap(manifestList.get(i), manifestList.get(j));
				j--;
			}
		}
		
	}
	
	/**
	 * Swap method used for sorting.
	 * @param item1 swap with item2
	 * @param item2 swap with item1
	 */
	public static void swap(Item item1, Item item2) {
		Item temp = item2;
		manifestList.set(manifestList.indexOf(item2), item1);
		manifestList.set(manifestList.indexOf(item1), temp);
	}
	
	/**
	 * optimizing the sort process by sorting the cold items according
	 * to their temperature.
	 * And, sorting the hot items as per their reorder amount.
	 */
	public static void optimizingSort() {
		int coldItem = getNumberOfItemNeedTemp();
		int n = manifestList.size()-1;
		//sorting cold items (according to temp)
		for (int i = 0; i < coldItem; i++){
			for (int j = 1; j < coldItem-i; j++){
				if (manifestList.get(j-1).getTemperature() > manifestList.get(j).getTemperature()) {
					swap(manifestList.get(j-1), manifestList.get(j));
				}
			}
		}
		//sorting hot items (according  to size)
		for (int i = coldItem; i < n; i++){
			for (int j = coldItem+1; j < n-i; j++){
				if (manifestList.get(j-1).getReorderAmount() > manifestList.get(j).getReorderAmount()) {
					swap(manifestList.get(j-1), manifestList.get(j));
				}
			}
		}
	}
	
}