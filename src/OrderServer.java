import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;

public class OrderServer implements FrontEndServerToOrderServer {
	private FrontEndServerToOrderServer stubOrder;
	private OrderServerToCatalogeServer stubCatalog;
	private Registry registry;
	static int OSPORT = 8885;
	static int CSPORT = 8884;
	public static void main(String args[]) {
		/*
		 * Create Order Server and its interface so that the front-end server
		 * can talk to it
		 */
		try {
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
			registry = LocateRegistry.getRegistry("localhost", CSPORT);
			stubCatalog = (OrderServerToCatalogeServer) registry.lookup("OrderServerToCatalogeServer");
		} catch (Exception e) {
			System.err.println("Order Server exception: " + e.toString());
		}
	}

	@Override
	public boolean buy(String itemNumber) throws RemoteException {
		// Query catalog to make sure the item is in stock and decrement count
		// by 1
		// Returns true if success and false if out of stock
		System.out.println("Buy method on order server has received a request");
		return true;
	}
}
