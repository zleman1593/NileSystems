
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrontEndServerToOrderServer extends Remote {
	boolean buy(String itemNumber) throws RemoteException;
}


