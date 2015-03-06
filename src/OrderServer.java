import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;


public class OrderServer implements FrontEndServerToOrderServer {
	//Stubs for connecting with order server and front-end server
	private FrontEndServerToOrderServer stubOrder;
	private OrderServerToCatalogeServer stubCatalog;
	private Registry registry;
	static int PORT = 8884;
	private ArrayList<ArrayList<String>> purchaseHistory;
	public static void main(String args[]) {
		/*
		 * Create Order Server and its interface so that the front-end server
		 * can talk to it
		 */
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", PORT);
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
			registry = LocateRegistry.getRegistry("localhost", PORT);
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
				if(purchaseHistory.get(i).get(0).equals(itemNumber))
				{//book has been purchased before
					//increment purchased counter
					purchaseHistory.get(i).set(1, "" + (1 + Integer.parseInt(purchaseHistory.get(i).get(1))));
				}
				else
				{//book has never been purchased before
					//create new element for textbook
					ArrayList<String> textInfo = new ArrayList<String>(2);
					textInfo.set(0, itemNumber);
					textInfo.set(1, "1");	
				}
			}
		}
		return result;
	}
}
