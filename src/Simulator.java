import java.rmi.RemoteException;

/*Class that creates new users/clients which engage with the service in a unique way allowing the simulation of simultaneous clients*/
public class Simulator {
	static int DEFAULT_PORT = 8886;
	static int port;
	static String ip = "54.175.34.191";
	public static void main(String[] args) throws RemoteException {
		if (args.length != 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = DEFAULT_PORT;
		}

		runBuy();
		runSearch();
		System.out.println("Single Thread Tests Complete");

		Runnable r1 = new Runnable() {
			public void run() {
				try {
					runBuy();
					runSearch();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
	
	
	//Start many Thread Test
	System.out.println("Start Many Thread Test");
	int threadCount = 30;
	Thread[] threads = new Thread[threadCount];
	for(int i = 0; i < threadCount; i++){
		threads[i] = new Thread(r1);
	}
	for(int i = 0; i < threadCount; i++){
		threads[i].start();
	}
	
	}
	// Results
	// Number of clients (buy time, search time)
	// 1   (5,1)
	// 2   (5,2)
	// 4   (8,3)
	// 10  (8,6)
	// 20  (15,11)
	// 30	(23,17)
	// 40  (30,21)
	// 50  (32,21)
	// 100	(63,41)
	// 500	(300,200)
	//Time is in ms

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
		System.out.println("Average Buy Response Time: " + averageTime);
	}

	private static void runSearch() throws RemoteException {
		int iterations = 500;
		Client second = new Client(ip, port);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			second.searchByTopic("college life");
		}
		long executionTime = System.currentTimeMillis() - startTime;
		long averageTime = executionTime / iterations;
		System.out.println("Average Search Response Time : " + averageTime);

	}

}
