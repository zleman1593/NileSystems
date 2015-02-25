import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;

public class FrontEndServer implements ClientToFronEndServer {
	private Registry registry;
	private FrontEndServerToOrderServer stubOrder;
	private FrontEndServerToCatalogServer stubCatalog;
	static int PORT = 8884;

	public static void main(String[] args) {

		/*
		 * Create FrontEndServer Server and its interface so that the client can
		 * talk to it
		 */
		try {
			FrontEndServer obj = new FrontEndServer();
			ClientToFronEndServer stub = (ClientToFronEndServer) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry("localhost", PORT);
			registry.bind("FrontEndServer", stub);
		} catch (Exception e) {
			System.err.println("Front-end Server exception when init Server: " + e.toString());
		}

	}

	// constructor
	public FrontEndServer() {
		try {
			registry = LocateRegistry.getRegistry("localhost", PORT);
			stubOrder = (FrontEndServerToOrderServer) registry.lookup("FrontEndServerToOrderServer");
			stubCatalog = (FrontEndServerToCatalogServer) registry
					.lookup("CatalogServer");
		} catch (Exception e) {
			System.err.println("Front-end Server exception when connecting to backend servers : " + e.toString());
		}

	}

	/* Attempts to buy one book. Returns false if not enough books available */
	@Override
	public synchronized ArrayList<String> buy(String itemNumber) throws RemoteException {
		System.out.println("Buy method on front-end server has received a request");
		return stubOrder.buy(itemNumber);
	}

	@Override
	public synchronized ArrayList<ArrayList<String>> search(String topic) throws RemoteException {
		return  stubCatalog.queryByTopic(topic);
	}

	@Override
	public synchronized ArrayList<String> lookup(String itemNumber) throws RemoteException {
		return stubCatalog.queryByItem(itemNumber);
	}

}
