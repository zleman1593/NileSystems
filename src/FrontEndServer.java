import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;

public class FrontEndServer implements ClientToFronEndServer {
	private Registry registryCS;
	private Registry registryOS;
	private FrontEndServerToOrderServer stubOrder;
	private FrontEndServerToCatalogServer stubCatalog;
	static int OSPORT = 8885;
	static int CSPORT = 8884;
	static int FSPORT = 8886;

	public static void main(String[] args) {

		/*
		 * Create FrontEndServer Server and its interface so that the client can
		 * talk to it
		 */
		try {
			FrontEndServer obj = new FrontEndServer();
			ClientToFronEndServer stub = (ClientToFronEndServer) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.createRegistry(FSPORT);
			registry.bind("FrontEndServer", stub);
		} catch (Exception e) {
			System.err.println("Front-end Server exception when init Server: " + e.toString());
		}

	}

	// constructor
	public FrontEndServer() {
		try {
			registryOS = LocateRegistry.getRegistry("localhost", OSPORT);
			registryCS = LocateRegistry.getRegistry("localhost", CSPORT);
			stubOrder = (FrontEndServerToOrderServer) registryOS.lookup("FrontEndServerToOrderServer");
			FrontEndServerToCatalogServer stubCatalog = (FrontEndServerToCatalogServer) registryCS
					.lookup("FrontEndServerToCatalogServer");
		} catch (Exception e) {
			System.err.println("Front-end Server exception when connecting to backend servers : " + e.toString());
		}

	}

	/* Attempts to buy one book. Returns false if not enough books available */
	@Override
	public boolean buy(String itemNumber) throws RemoteException {
		System.out.println("Buy method on front-end server has received a request");
		return stubOrder.buy(itemNumber);
	}

	@Override
	public ArrayList<ArrayList<String>> search(String topic) throws RemoteException {
		return new ArrayList<ArrayList<String>>();// stubCatalog.query(topic);

	}

	@Override
	public ArrayList<String> lookup(String itemNumber) throws RemoteException {
		return stubCatalog.queryByItem(itemNumber);
	}

}
