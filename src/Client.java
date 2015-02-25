import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;


public class Client {
	private ClientToFronEndServer stub;
	static int PORT = 8884;
	//Constructor
	public Client(String host) {
		try {
			Registry registry = LocateRegistry.getRegistry(host, PORT );
			stub = (ClientToFronEndServer) registry.lookup("FrontEndServer");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
		}
	}

	public void actionOne() throws RemoteException {
		System.out.println("Action One invoked from First Client");
		ArrayList<String> result = stub.buy("57471");
		if(result.get(0).equalsIgnoreCase("-1"))
		{
			if(result.get(1).equalsIgnoreCase("out of stock"))
			{
				System.out.println("Out of Stock");
				return;
			}
			if(result.get(1).equalsIgnoreCase("invalid itemNumber"))
			{
				System.out.println("Invalid Item Number");
				return;
			}
		}else{
			System.out.println("Bought Book:" + result.get(0));
		}
	}
	
	public void actionTwo() throws RemoteException {
		System.out.println("Action Two invoked from Second Client");
			ArrayList<ArrayList<String>> result = stub.search("Distributed Systems for Dummies");
			if(result.size() == 0){
				System.out.println("Could not find any books with those search words");
			}else{
				for(int i = 0; i < result.size(); i++)
				{
					System.out.println(result.get(0).get(0) + ": Item Number " + result.get(0).get(1));
				}	
			}
		
	}
	
	
	public void actionThree() throws RemoteException {
		System.out.println("Action Three invoked from Third Client");
		ArrayList<String> result = stub.lookup("57471");
		if(result.get(0).equalsIgnoreCase("-1"))
		{
			if(result.get(1).equalsIgnoreCase("invalid itemNumber"))
			{
				System.out.println("Invalid itemNumber");
				return;
			}
		}
		for(int i = 0; i < 4; i++)
		{
			System.out.println(result.get(i));
		}
	}
	
	public void actionFour() throws RemoteException {
		System.out.println("Action Four invoked from Fourth Client");
		ArrayList<ArrayList<String>> result = stub.search("systems");
		if(result.size() == 0){
			System.out.println("Could not find any books with those search words");
		}else{
			for(int i = 0; i < result.size(); i++)
			{
				System.out.println(result.get(i).get(0) + ": Item Number " + result.get(i).get(1));
			}	
		}
	}

}