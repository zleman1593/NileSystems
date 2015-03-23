import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {
	private ClientToFronEndServer stub;
	private int port;


	// Constructor
	public Client(String host, int port) {
		try {
			this.port = port;
			Registry registry = LocateRegistry.getRegistry(host, port);
			stub = (ClientToFronEndServer) registry.lookup("FrontEndServer");
		} catch (Exception e) {
//			System.err.println("Client exception: " + e.toString());
		}
	}

	/*Is given an id of a book, and will attempt to purchase book. Responds with a success of failure message*/
	public void buy(String id) throws RemoteException {

		ArrayList<String> result = stub.buy(id);
		if (result.get(0).equalsIgnoreCase("-1")) {
			if (result.get(1).equalsIgnoreCase("out of stock")) {
//				System.out.println("Out of Stock");
				return;
			}
			if (result.get(1).equalsIgnoreCase("invalid itemNumber")) {
//				System.out.println("Invalid Item Number");
				return;
			}
		} else {
//			System.out.println("Bought Book: " + result.get(3));
		}
//		System.out.println("");
	}

	/*Is given a book Id and returns info about book if found, or that a book with that ID could not be found*/
	public void lookUpById(String id) throws RemoteException {
//		System.out.println("Looking up book with ID " + id);
		ArrayList<String> result = stub.lookup(id);
		if (result.get(0).equalsIgnoreCase("-1")) {
			if (result.get(1).equalsIgnoreCase("invalid itemNumber")) {
//				System.out.println("Invalid itemNumber");
				return;
			}
//			System.out.println("");
		}

//		System.out.println(result.get(3));
//		System.out.println("Price: " + result.get(1));
//		System.out.println("Stock: " + result.get(0));
	}

	/*Is given a string of search terms and  info about all books that have search terms that match their topic is printed */
	public void searchByTopic(String searchTerm) throws RemoteException {
		ArrayList<ArrayList<String>> result = stub.search(searchTerm);
		if (result.size() == 0) {
//			System.out.println("Could not find any books with those search words for their topics: " + searchTerm);
//			System.out.println("");
		} else {
//			System.out.println("Books whose topic matches: " + searchTerm);
			for (int i = 0; i < result.size(); i++) {
//				System.out.println(result.get(i).get(3));
//				System.out.println("Item Number " + result.get(i).get(2));
//				System.out.println("Price: " + result.get(i).get(1));
//				System.out.println("Stock: " + result.get(i).get(0));
//				System.out.println("");
			}
		}
	}

}