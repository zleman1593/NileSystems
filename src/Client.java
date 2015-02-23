import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	private ClientToFronEndServer stub;
	static int FSPORT = 8886;
	//Constructor
	public  Client(String host) {
		try {
			Registry registry = LocateRegistry.getRegistry(host, FSPORT );
			stub = (ClientToFronEndServer) registry.lookup("FrontEndServer");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
		}
	}

	public void actionOne() throws RemoteException {
		System.out.println("Action One invoked from First Client");
		if (!stub.buy("57471")) {
			System.out.println("Out of Stock");
		}
	}
	
	public void actionTwo() throws RemoteException {
		System.out.println("Action Two invoked from Second Client");
		
			if(!stub.search("Distributed Systems for Dummies").get(0).equalsIgnoreCase("-1")){
				System.out.println("Found Book");
			}else{
				System.out.println("Out of Stock");
			}
		
	}
	
	
	public void actionThree() throws RemoteException {
		System.out.println("Action Three invoked from Third Client");
		System.out.println(stub.lookup("57471"));
	}

}