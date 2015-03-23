import java.rmi.RemoteException;

/*Class that creates new users/clients which engage with the service in a unique way allowing the simulation of simultaneous clients*/
public class Simulator {
	static int DEFAULT_PORT = 8884;
	static int port;
	static String ip = "54.172.38.98";
	public static void main(String[] args) throws RemoteException {
		if (args.length != 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = DEFAULT_PORT;
		}

		runBuy();
		runSearch();
		System.out.print("Single Thread Tests Complete");

		Runnable r1 = new Runnable() {
			public void run() {
				try {
					runBuy();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// runSearch();
			}
		};
		Runnable r2 = new Runnable() {
			public void run() {
				try {
					runBuy();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// runSearch();
			}
		};
		Runnable r3 = new Runnable() {
			public void run() {
				try {
					runBuy();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// runSearch();
			}
		};
		Runnable r4 = new Runnable() {
			public void run() {
				try {
					runBuy();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// runSearch();
			}
		};

		//Start 2 Thread Test
		System.out.print("Start 2 Thread Test");
		Thread thr1 = new Thread(r1);
		Thread thr2 = new Thread(r2);

		
		thr1.start();
		thr2.start();
	
		
		try {
			thr1.join();
			thr2.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Start 4 Thread Test
		System.out.print("Start 4 Thread Test");
		Thread thrA = new Thread(r1);
		Thread thrB = new Thread(r2);
		Thread thrC = new Thread(r3);
		Thread thrD = new Thread(r4);
		
		thrA.start();
		thrB.start();
		thrC.start();
		thrD.start();
		
		try {
			thrA.join();
			thrB.join();
			thrC.join();
			thrD.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * // Third client Client third = new Client("localhost", port);
	 * third.searchByTopic("horses"); // Fourth Client Client fourth = new
	 * Client("localhost", port); fourth.searchByTopic("systems");
	 * fourth.searchByTopic("college life");
	 */

	private static void runBuy() throws RemoteException {
		int iterations = 500;
		Client first = new Client(ip, port);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			first.buy("57471");
		}
		long executionTime = System.currentTimeMillis() - startTime;
		long averageTime = executionTime / iterations;
		System.out.print("Average Response Time: " + averageTime);
	}

	private static void runSearch() throws RemoteException {
		int iterations = 500;
		Client second = new Client(ip, port);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			second.lookUpById("57471");
		}
		long executionTime = System.currentTimeMillis() - startTime;
		long averageTime = executionTime / iterations;
		System.out.print("Average Response Time: " + averageTime);

	}

}
