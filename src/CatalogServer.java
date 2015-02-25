import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CatalogServer implements OrderServerToCatalogeServer,FrontEndServerToCatalogServer {
	static int PORT = 8884;
	// list to hold information about the 4 books
	private ArrayList<ArrayList<String>> itemList;

	// hold information about the four books
	public static void main(String args[]) {
		try {
			CatalogServer obj = new CatalogServer();
			//CatalogServer obj2 = new CatalogServer();
			Remote stubForOrder = UnicastRemoteObject.exportObject(obj, 0);
			//FrontEndServerToCatalogServer stubForFront = (FrontEndServerToCatalogServer) UnicastRemoteObject.exportObject(obj2, 0);
			Registry registry = LocateRegistry.createRegistry(PORT);
			//registry.bind("OrderServerToCatalogeServer", stubForOrder);
			registry.bind("CatalogServer", stubForOrder);
//			registry.bind("FrontEndServerToCatalogServer", stubForFront);

		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
		}
	}

	// Constructor
	public CatalogServer() {

		itemList = new ArrayList<ArrayList<String>>(4);

		// create textbook elements and add them to itemList
		ArrayList<String> input1 = new ArrayList<String>(4);
		ArrayList<String> input2 = new ArrayList<String>(4);
		ArrayList<String> input3 = new ArrayList<String>(4);
		ArrayList<String> input4 = new ArrayList<String>(4);

		input1.add("5");
		input1.add("101");
		input1.add("57471");
		input1.add("Achieving Less Bugs with More Hugs in CSCI 3325");
		input1.add(" distributed systems");
		
		input2.add("5");
		input2.add("121");
		input2.add("58574");
		input2.add("Distributed Systems for Dummies");
		input2.add(" distributed systems");

		input3.add("5");
		input3.add("141");
		input3.add("12395");
		input3.add("Surviving College");
		input3.add("college life");
		
		input4.add("5");
		input4.add("161");
		input4.add("13298");
		input4.add("Cooking for the Impatient Undergraduate");
		input4.add("college life");

		itemList.add(input1);
		itemList.add(input2);
		itemList.add(input3);
		itemList.add(input4);

	}

	//searches for an item by its topic
	public ArrayList<ArrayList<String>> queryByTopic(String topic)
	{
		ArrayList<ArrayList<String>> returnList  = new ArrayList<ArrayList<String>>();
		String[] arr = topic.split(" ");
		for(String ss : arr)
		{
			//for each word, look for matching words in textbook title
			for(int i = 0; i < itemList.size() ; i++)
			{
				//handle repeated finds
				if(returnList.contains(itemList.get(i)))
				{
					continue;
				}
				
				if(itemList.get(i).get(3).contains(ss) || itemList.get(i).get(4).contains(ss) )
				{
					 returnList.add(itemList.get(i));
				}
			}
		}
		return returnList;
	}
	
	// searches for an item by its number
	public ArrayList<String> queryByItem(String iD) {
		ArrayList<String> itemInfo;
		
		//want to prevent textbook info from being changed
		synchronized(this){
			for (int i = 0; i < itemList.size(); i++) {
				itemInfo = itemList.get(i);
				if (itemInfo.contains(iD)) {
					return itemInfo;
				}
			}
		}

		itemInfo = new ArrayList<String>(1);
		itemInfo.add("invalid itemNumber");
		itemInfo.add("-1");
		return itemInfo;
	}

	

	// updates the stock of an item
	public synchronized ArrayList<String> updateStock(String itemNumber, String newNum) {
		ArrayList<String> textElement;
		
		for (int i = 0; i < itemList.size(); i++) {
			textElement = itemList.get(i);
			if (textElement.contains(Integer.parseInt(itemNumber))) {
				int oldStock = Integer.parseInt(textElement.get(3));
				if(oldStock + Integer.parseInt(newNum) < 0)
				{
					//no more books to sell
					textElement = new ArrayList<String>(2);
					textElement.add("out of stock");
					textElement.add("-1");
					return textElement;
				}
				textElement.set(3, ""+oldStock);
				return itemList.get(i);
			}
		}
		
		textElement = new ArrayList<String>(2);
		textElement.add("invalid itemNumber");
		textElement.add("-1");
		return textElement;
	}
	
	
	
	//Updates the price of an item
		public synchronized ArrayList<String> updatePrice(String itemNumber, String newPrice) {
			ArrayList<String> textElement;

			for (int i = 0; i < itemList.size(); i++) {
				textElement = itemList.get(i);
				if (textElement.contains(Integer.parseInt(itemNumber))) {
					textElement.set(2, newPrice);
					return itemList.get(i);
				}
			}

			textElement = new ArrayList<String>(2);
			textElement.add("invalid itemNumber");
			textElement.add("-1");
			return textElement;
		}

}
