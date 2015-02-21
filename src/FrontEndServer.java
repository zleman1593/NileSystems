
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.*;



public class FrontEndServer {
	private FrontEndServerToOrderServer stub;
	 public static void main(String[] args) {
		    String host = (args.length < 1) ? "localhost" : args[0];
		    try {
		      Registry registry = LocateRegistry.getRegistry(host, 8888);
		      FrontEndServerToOrderServer stub = (FrontEndServerToOrderServer) registry.lookup("FrontEndServerToOrderServer");
		    } catch (Exception e) {
		        System.err.println("Client exception: " + e.toString());
		    }
		  }

	 
	 
	 
	
	public ArrayList<ArrayList<String>> search(String topic)  throws RemoteException{
		
		return ;
	}
	
	
	
	public ArrayList<ArrayList<String>> lookup (int itemNumber) throws RemoteException {
		
		return ;
	}
	
	/*Attempts to buy one book. Returns false if not enough books available*/
	public boolean buy(int itemNumber) throws RemoteException {
		boolean sucess = true;
		sucess = stub.buy(itemNumber);
		return sucess;
	}
	
}
