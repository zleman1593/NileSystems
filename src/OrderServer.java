import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;

public class OrderServer implements FrontEndServerToOrderServer {
	private OrderServerToCatalogeServer stub;

	public static void main(String args[]) {
		/*
		 * Create Order Server and its interface so that the front-end server
		 * can talk to it
		 */
		try {
			OrderServer obj = new OrderServer();
			orderServerToCatalogServer stub = (orderServerToCatalogServer) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.createRegistry(8888);
			registry.bind("OrderServer", stub);
		} catch (Exception e) {
			System.err.println("Order Server exception: " + e.toString());
		}

		
		//Connect to the interface provided by the catalog server
		
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 8888);
			 stub = (OrderServerToCatalogeServer) registry
					.lookup("OrderServerToCatalogeServer");
		} catch (Exception e) {
			System.err.println("Order Server exception: " + e.toString());
		}

	}

	@Override
	public boolean buy(int itemNumber) throws RemoteException {
		// Query catalog to make sure the item is in stock and decrement count
		// by 1
		// Returns true if success and false if out of stock

		return false;
	}
}
