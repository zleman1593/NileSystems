import java.rmi.RemoteException;

/*Class that creates new users/clients which engage with the service in a unique way allowing the simulation of simultaneous clients*/
public class Simulator {
	static int DEFAULT_PORT  = 8884;
	static int port;
	public static void main(String[] args) throws RemoteException {
		if (args.length != 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port =  DEFAULT_PORT;
		}
		//First client
		Client first = new Client("localhost",port);
		first.buy("57471");
		first.buy("57471");
		first.buy("57471");
		first.buy("57471");
		first.buy("57471");
		first.buy("57471");

		//Second client
		Client second = new Client("localhost",port);
		second.lookUpById("57471");

		//Third client
		Client third = new Client("localhost",port);
		third.searchByTopic("horses");
		//Fourth Client
		Client fourth = new Client("localhost",port);
		fourth.searchByTopic("systems");
		fourth.searchByTopic("college life");
		
	
	}
}
