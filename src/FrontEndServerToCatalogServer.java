
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ArrayList;
public interface FrontEndServerToCatalogServer extends Remote {
	ArrayList<String> queryByItem(String iD) throws RemoteException;
	ArrayList<ArrayList<String>> queryByTopic(String topic) throws RemoteException;
}


