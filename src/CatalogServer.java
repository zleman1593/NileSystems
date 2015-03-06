import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CatalogServer implements OrderServerToCatalogeServer, FrontEndServerToCatalogServer {
	static int port;
	static int DEFAULT_PORT  = 8884;
	// list to hold information about the 4 books
	private ArrayList<ArrayList<String>> itemList;

	//create registry using port 8884 for other servers to add to
	// hold information about the four books
	public static void main(String args[]) {
		try {
			if (args.length != 0) {
				port = Integer.parseInt(args[0]);
			} else {
				port =  DEFAULT_PORT;
			}
			CatalogServer obj = new CatalogServer();
			Remote stubForOrder = UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.createRegistry(port);
			registry.bind("CatalogServer", stubForOrder);

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
		input1.add("distributed systems");

		input2.add("5");
		input2.add("121");
		input2.add("58574");
		input2.add("Distributed Systems for Dummies");
		input2.add("distributed systems");

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

	// searches for an item by its topic
	public ArrayList<ArrayList<String>> queryByTopic(String topic) {
		ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>();
		String[] arr = topic.split(" ");
		for (String ss : arr) {
			// for each word, look for matching words in textbook title
			
			loop1: for (int i = 0; i < itemList.size(); i++) {
				//looking at a specific textbook. Don't want it to be changed midway
				synchronized (this) {
				if (itemList.get(i).get(4).contains(ss)) {
					//handle repeated finds
					//look through already found textbooks to see if textbook is already found 
					for(int j = 0; j < returnList.size(); j++)
					{
						if(returnList.get(j).get(2).equals(itemList.get(i).get(2)))
						{//if textbook already found, skip this textbook
							continue loop1;
						}
					}
					//matching textbook topic with search word
					returnList.add(itemList.get(i));
				}
				}
			}
		}
		return returnList;
	}

	// searches for an item by its number
	public ArrayList<String> queryByItem(String iD) {
		ArrayList<String> itemInfo;

		/*did not synchronized because item number is never changed
		so there is no fear of inaccurate data*/
			for (int i = 0; i < itemList.size(); i++) {
				itemInfo = itemList.get(i);
				if (itemInfo.contains(iD)) 
				{//if item has matching ID
					return itemInfo;
				}
			}

		//returns information notifying client that item number not found
		itemInfo = new ArrayList<String>(1);
		itemInfo.add("-1");
		itemInfo.add("invalid itemNumber");
		return itemInfo;
	}

	// updates the stock of an item
	public ArrayList<String> updateStock(String itemNumber, String newNum) {
		ArrayList<String> textElement;

		for (int i = 0; i < itemList.size(); i++) {
			/*potentially changing information about textbook, so can't let
			information be changed by another source midway*/
			synchronized (this) {
			textElement = itemList.get(i);
			
			if (textElement.contains(itemNumber)) 
			{//if the item has been found
				//change stock value to an int
				int oldStock = Integer.parseInt(textElement.get(0));
			
				if (oldStock + Integer.parseInt(newNum) < 0) {
					// no more books to sell
					textElement = new ArrayList<String>(2);
					textElement.add("-1");
					textElement.add("out of stock");
					return textElement;
				}

				//updates the stock
				itemList.get(i).set(0, "" + (oldStock + Integer.parseInt(newNum)));
				return itemList.get(i);

				}
			}
		}

		//could not find item, so return invalid result
		textElement = new ArrayList<String>(2);
		textElement.add("-1");
		textElement.add("invalid itemNumber");
		return textElement;
	}

	// Updates the price of an item
	public ArrayList<String> updatePrice(String itemNumber, String newPrice) {
		ArrayList<String> textElement;

		for (int i = 0; i < itemList.size(); i++) {
			/*updating price about textbook, so cannot let anyone access midway
			until price is changed*/
			synchronized (this) {
				textElement = itemList.get(i);

				if (textElement.contains(Integer.parseInt(itemNumber))) 
				{//item found
					//set new price
					textElement.set(2, newPrice);
					return itemList.get(i);
				}
			}
		}

		//could not find item. return invalid result
		textElement = new ArrayList<String>(2);
		textElement.add("-1");
		textElement.add("invalid itemNumber");
		return textElement;
	}

}
