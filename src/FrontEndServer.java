import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;

public class FrontEndServer implements ClientToFronEndServer {
	private Registry registry;
	private FrontEndServerToOrderServer stubOrder;
	private FrontEndServerToCatalogServer stubCatalog;
	static int port;
	static int DEFAULT_PORT  = 8884;

	public static void main(String[] args) {

		/*
		 * Create FrontEndServer Server and its interface so that the client can
		 * talk to it
		 */
		try {
			if (args.length != 0) {
				port = Integer.parseInt(args[0]);
			} else {
				port =  DEFAULT_PORT;
			}
			FrontEndServer obj = new FrontEndServer();
			ClientToFronEndServer stub = (ClientToFronEndServer) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry("localhost", port);
			registry.bind("FrontEndServer", stub);
		} catch (Exception e) {
			System.err.println("Front-end Server exception when init Server: " + e.toString());
		}

	}

	// constructor
	public FrontEndServer() {
		try {
			registry = LocateRegistry.getRegistry("localhost", port);
			stubOrder = (FrontEndServerToOrderServer) registry.lookup("FrontEndServerToOrderServer");
			stubCatalog = (FrontEndServerToCatalogServer) registry
					.lookup("CatalogServer");
		} catch (Exception e) {
			System.err.println("Front-end Server exception when connecting to backend servers : " + e.toString());
		}

	}

	/* Attempts to buy one book. Returns error if not enough books available or if book does not exist */
	@Override
	public  ArrayList<String> buy(String itemNumber) throws RemoteException {
		System.out.println("Buy method on front-end server has received a request");
		return stubOrder.buy(itemNumber);
	}

	/*Searches using the catalog server for books matching the topic string*/
	@Override
	public  ArrayList<ArrayList<String>> search(String topic) throws RemoteException {
		return  stubCatalog.queryByTopic(topic);
	}

	/* Searches for an item by its Id number. Returns error if book does not exist. Returns info about all matching books*/
	@Override
	public  ArrayList<String> lookup(String itemNumber) throws RemoteException {
		return stubCatalog.queryByItem(itemNumber);
	}

}
