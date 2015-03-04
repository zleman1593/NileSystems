import java.rmi.RemoteException;

/*Class that creates new users/clients which engage with the service in a unique way allowing the simulation of simultaneous clients*/
public class Simulator {
	public static void main(String[] args) throws RemoteException {
		//First client
		Client first = new Client("localhost");
		first.actionOne();
		first.actionOne();
		first.actionOne();
		first.actionOne();
		first.actionOne();
		first.actionOne();
		first.actionOne();
		//Create delay
		//Second client
		Client second = new Client("localhost");
		second.actionTwo();
		//Create delay
		//Third client
		Client third = new Client("localhost");
		third.actionThree();
		//Fourth Client
		Client fourth = new Client("localhost");
		fourth.actionFour();
		fourth.actionFive();
		
		
	
	}
}
