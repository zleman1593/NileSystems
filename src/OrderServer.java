import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.*;

public class OrderServer implements FrontEndServerToOrderServer {
	// Stubs for connecting with order server and front-end server
	private FrontEndServerToOrderServer stubOrder;
	private OrderServerToCatalogeServer stubCatalog;
	private Registry registry;
	static int port;
	static int DEFAULT_PORT  = 8884;

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

			Registry registry = LocateRegistry.getRegistry("localhost", port);
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
			
			registry = LocateRegistry.getRegistry("localhost", port);
			stubCatalog = (OrderServerToCatalogeServer) registry.lookup("CatalogServer");
		} catch (Exception e) {
			System.err.println("Order Server exception: " + e.toString());
		}
	}

	@Override
	public ArrayList<String> buy(String itemNumber) throws RemoteException {
		/*
		 * update stock by decrementing count by 1. returns invalid information
		 * if cannot purchase
		 */
		return stubCatalog.updateStock(itemNumber, "-1");

	}
}
