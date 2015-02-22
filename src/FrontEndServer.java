import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;

public class FrontEndServer implements ClientToFronEndServer {
	private Registry registry;
	private FrontEndServerToOrderServer stubOrder;
	private FrontEndServerToCatalogServer stubCatalog;
	static int PORT = 8887;
	public static void main(String[] args) {

		/*
		 * Create FrontEndServer Server and its interface so that the client can
		 * talk to it
		 */
		try {
			FrontEndServer obj = new FrontEndServer();
			ClientToFronEndServer stub = (ClientToFronEndServer) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("FrontEndServer", stub);
		} catch (Exception e) {
			System.err.println("Front-end Server exception: " + e.toString());
		}

	}

	// constructor
	public FrontEndServer() {
		// String host = (args.length < 1) ? "localhost" : args[0];
		try {
			registry = LocateRegistry.getRegistry("localhost", PORT);
			FrontEndServerToOrderServer stubOrder = (FrontEndServerToOrderServer) registry
					.lookup("FrontEndServerToOrderServer");
			FrontEndServerToCatalogServer stubCatalog = (FrontEndServerToCatalogServer) registry
					.lookup("FrontEndServerToCatalogServer");
		} catch (Exception e) {
			System.err.println("Front-end Server exception: " + e.toString());
		}

	}

	/* Attempts to buy one book. Returns false if not enough books available */
	@Override
	public boolean buy(String itemNumber) throws RemoteException {
		boolean sucess = true;
		sucess = stubOrder.buy(itemNumber);
		return sucess;
	}

	@Override
	public ArrayList<ArrayList<String>> search(String topic) throws RemoteException {
		return new ArrayList<ArrayList<String>>();//stubCatalog.query(topic);
		
	}

	@Override
	public ArrayList<String> lookup(String itemNumber) throws RemoteException {

		return stubCatalog.queryByItem(itemNumber);
	}

}
