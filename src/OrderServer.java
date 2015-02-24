import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;

public class OrderServer implements FrontEndServerToOrderServer {
	private FrontEndServerToOrderServer stubOrder;
	private OrderServerToCatalogeServer stubCatalog;
	private Registry registry;
	static int PORT = 8884;
	public static void main(String args[]) {
		/*
		 * Create Order Server and its interface so that the front-end server
		 * can talk to it
		 */
		try {
			//Registry registry = LocateRegistry.createRegistry(OSPORT);
			Registry registry = LocateRegistry.getRegistry("localhost", PORT);
			OrderServer obj = new OrderServer();
			FrontEndServerToOrderServer stubOrder = (FrontEndServerToOrderServer) UnicastRemoteObject.exportObject(obj,
					0);
			
			registry.bind("FrontEndServerToOrderServer", stubOrder);
		} catch (Exception e) {
			System.err.println("Order Server exception: " + e.toString());
		}

	}

	public synchronized OrderServer() {
		// Connect to the interface provided by the catalog server
		try {
			registry = LocateRegistry.getRegistry("localhost", PORT);
			stubCatalog = (OrderServerToCatalogeServer) registry.lookup("CatalogServer");
		} catch (Exception e) {
			System.err.println("Order Server exception: " + e.toString());
		}
	}

	@Override
	public synchronized boolean buy(String itemNumber) throws RemoteException {
		// Query catalog to make sure the item is in stock and decrement count by 1
		if(!stubCatalog.queryByItem(itemNumber).get(0).equalsIgnoreCase("-1")){
			stubCatalog.updateStock(itemNumber, "-1");
			System.out.println("Purchased Book");
			return true;
		}else{

		// Returns true if success and false if out of stock
		System.out.println("Book purchase could not be completed at this time.");
		return false;
		}
		
	}
}
