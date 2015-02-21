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
		        System.err.println("Front-end Server exception: " + e.toString());
		    }
		    
		    
		    try {
			      Registry registry = LocateRegistry.getRegistry(host, 8888);
			      FrontEndServerToCatalogServer stub = (FrontEndServerToCatalogServer) registry.lookup("FrontEndServerToCatalogServer");
			    } catch (Exception e) {
			        System.err.println("Front-end Server exception: " + e.toString());
			    }
		    
		    
			/*
			 * Create FrontEndServer Server and its interface so that the client
			 * can talk to it
			 */
			try {
				FrontEndServer obj = new FrontEndServer();
				ClientToFronEndServer stub = (ClientToFronEndServer) UnicastRemoteObject.exportObject(obj, 0);
				Registry registry = LocateRegistry.createRegistry(8888);
				registry.bind("FrontEndServer", stub);
			} catch (Exception e) {
				System.err.println("Order Server exception: " + e.toString());
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
