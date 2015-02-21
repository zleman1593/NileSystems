import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	private ClientToFronEndServer stub;

	public static void main(String[] args) {
		String host = (args.length < 1) ? "localhost" : args[0];
		try {
			Registry registry = LocateRegistry.getRegistry(host, 8888);
			stub = (ClientToFronEndServer) registry.lookup("FrontEndServer");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
		}
	}

	public void actionOne() throws RemoteException {

		if (!stub.buy(57471)) {
			System.out.println("Out of Stock");
		}
	}

}
