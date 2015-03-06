import java.rmi.RemoteException;

/*Class that creates new users/clients which engage with the service in a unique way allowing the simulation of simultaneous clients*/
public class Simulator {
	public static void main(String[] args) throws RemoteException {
		//First client
		Client first = new Client("localhost");
		first.buy("57471");
		first.buy("57471");
		first.buy("57471");
		first.buy("57471");
		first.buy("57471");
		first.buy("57471");

		//Second client
		Client second = new Client("localhost");
		second.lookUpById("57471");

		//Third client
		Client third = new Client("localhost");
		third.searchByTopic("horses");
		//Fourth Client
		Client fourth = new Client("localhost");
		fourth.searchByTopic("systems");
		fourth.searchByTopic("college life");
		
	
	}
}
