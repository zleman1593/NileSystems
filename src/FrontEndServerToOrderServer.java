
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FrontEndServerToOrderServer extends Remote {
	ArrayList<String> buy(String itemNumber) throws RemoteException;
}


