import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CatalogServer implements OrderServerToCatalogeServer{
	
	//list to hold information about the 4 books
	private ArrayList<ArrayList<String>> itemList;
	
	//searches for an item by its number or by topic
	public ArrayList<String> queryByItem(String iD)
	{
		ArrayList<String> itemInfo;
		
		for(int i = 0; i<4; i++)
		{
			itemInfo = itemList.get(i);
			if(itemInfo.contains(iD))
			{
				return itemInfo;
			}
		}
		itemInfo = new ArrayList<String>(1);
		itemInfo.add("-1");
		return itemInfo;
	}
	
	//upates the price of an item
	public ArrayList<String> updatePrice(string itemNumber, string newPrice)
	{
		ArrayList<ArrayList <String>> textElement;
		
		for(int i = 0; i<4; i++)
		{
			textElement = itemList.get(i);
			if(textElement.contains(intNumber))
			{
				return itemInfo;
			}
		}
		
		textElement = new Arraylist<Arraylist <String>>(3);
		textElement.add("invalid itemNumber");
		textElement.add("-1");
		return textElement;
	}
	//updates the stock of an item
	public updateStock(string itemNumber, string newNum)
	{
		
	}
	//hold information about the four books
	  public static void main(String args[]) {
		    try {
		    	CatalogServer obj = new CatalogServer();
		      Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
		      Registry registry = LocateRegistry.createRegistry(8888);
		      registry.bind("Hello", stub);
		      itemList = new ArrayList<ArrayList<String>>(4);
		      
		      //create textbook elements and add them to itemList
		      ArrayList<String> input1 = new ArrayList<String>(3);
		      ArrayList<String> input2 = new ArrayList<String>(3);
		      ArrayList<String> input3 = new ArrayList<String>(3);
		      ArrayList<String> input4 = new ArrayList<String>(3);
		      
		      input1.add("Achieving Less Bugs with More Hugs in CSCI 3325");
		      input1.add("57471");
		      input1.add("5");
		      input2.add("Distributed Systems for Dummies");
		      input2.add("58574");
		      input2.add("5");
		      input3.add("Surviving College");
		      input3.add("12395");
		      input3.add("5");
		      input4.add("Cooking for the Impatient Undergraduate");
		      input4.add("13298");
		      input4.add("5");
		      
		      itemList.add(input1);
		      itemList.add(input2);
		      itemList.add(input3);
		      itemList.add(input4);
		      
		      
		    } catch (Exception e) {
		      System.err.println("Server exception: " + e.toString());
		    }
		  }
}

