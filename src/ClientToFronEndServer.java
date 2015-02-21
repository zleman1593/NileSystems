
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ClientToFronEndServer extends Remote {
	ArrayList<ArrayList<String>> search(String topic) throws RemoteException;
	ArrayList<ArrayList<String>> lookup(int itemNumber) throws RemoteException;
	boolean buy(int itemNumber) throws RemoteException;
}

