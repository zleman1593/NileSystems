
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrontEndServerToOrderServer extends Remote {
	boolean buy(int itemNumber) throws RemoteException;
}


