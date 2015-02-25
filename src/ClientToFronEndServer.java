
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ClientToFronEndServer extends Remote {
	ArrayList<String> search(String topic) throws RemoteException;
	ArrayList<String> lookup(String itemNumber) throws RemoteException;
	ArrayList<String> buy(String itemNumber) throws RemoteException;
}

