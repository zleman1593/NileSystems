import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;

public class OrderServer implements FrontEndServerToOrderServer {
	// Stubs for connecting with order server and front-end server
	private FrontEndServerToOrderServer stubOrder;
	private OrderServerToCatalogeServer stubCatalog;
	private Registry registry;
	private ArrayList<ArrayList<String>> purchaseHistory;
	static int port;
	static int DEFAULT_PORT  = 8884;
	static int RESTOCKTHRESH = 3;
	static String RESTOCKNUM = "3";
	static int OSPORT = 8885;
	static int CSPORT = 8884;
	static String ip = "54.172.38.98";
	public static void main(String args[]) {
		/*
		 * Create Order Server and its interface so that the front-end server
		 * can talk to it
		 */
		try {
			if (args.length != 0) {
				port = Integer.parseInt(args[0]);
			} else {
				port =  DEFAULT_PORT;
			}

			Registry registry = LocateRegistry.createRegistry(OSPORT);
			OrderServer obj = new OrderServer();
			FrontEndServerToOrderServer stubOrder = (FrontEndServerToOrderServer) UnicastRemoteObject.exportObject(obj,
					0);

			registry.bind("FrontEndServerToOrderServer", stubOrder);
		} catch (Exception e) {
			System.err.println("Order Server exception: " + e.toString());
		}

	}

	public OrderServer() {
		// Connect to the interface provided by the catalog server
		try {
			
			registry = LocateRegistry.getRegistry(ip, CSPORT);
			stubCatalog = (OrderServerToCatalogeServer) registry.lookup("CatalogServer");
			purchaseHistory = new ArrayList<ArrayList<String>>(4);
		} catch (Exception e) {
			System.err.println("Order Server exception: " + e.toString());
		}
	}

	@Override
	public  ArrayList<String> buy(String itemNumber) throws RemoteException {
		/*update stock by decrementing count by 1. returns invalid information if
		cannot purchase*/
		ArrayList<String> result = stubCatalog.updateStock(itemNumber, "-1");
		if (!result.get(0).equalsIgnoreCase("-1")) {
			//valid purchase. Log purchase
			for(int i = 0; i < purchaseHistory.size(); i++)
			{
				synchronized(this){
					
				if(purchaseHistory.get(i).get(0).equals(itemNumber))
				{//book has been purchased before
					//increment purchased counter
					int num = Integer.parseInt(purchaseHistory.get(i).get(1));
					if(0 == num % RESTOCKTHRESH)
					{//3 books have been purchased since last restock
						//restock
						stubCatalog.updateStock(itemNumber, RESTOCKNUM);
					}
					//a book has been purchased. increment counter
					purchaseHistory.get(i).set(1, "" + (1 + num));
					return result;
				}
				}
			}
			//book has never been purchased before
			//create new element for textbook
			ArrayList<String> textInfo = new ArrayList<String>(2);
			textInfo.add(itemNumber);
			textInfo.add("1");
			purchaseHistory.add(textInfo);
		}
		return result;
	}
}
