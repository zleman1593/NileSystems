import java.rmi.RemoteException;

/*Class that creates new users/clients which engage with the service in a unique way allowing the simulation of simultaneous clients*/
public class Simulator {
	static int DEFAULT_PORT = 8884;
	static int port;

	public static void main(String[] args) throws RemoteException {
		if (args.length != 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = DEFAULT_PORT;
		}

		runBuy();
		runSearch();

		Runnable r1 = new Runnable() {
			public void run() {
				runBuy();
				// runSearch();
			}
		};
		Runnable r2 = new Runnable() {
			public void run() {
				runBuy();
				// runSearch();
			}
		};
		Runnable r3 = new Runnable() {
			public void run() {
				runBuy();
				// runSearch();
			}
		};
		Runnable r4 = new Runnable() {
			public void run() {
				runBuy();
				// runSearch();
			}
		};

		// Starts the 4 threads
		/*
		Thread thr1 = new Thread(r1);
		Thread thr2 = new Thread(r2);
		Thread thr3 = new Thread(r3);
		Thread thr4 = new Thread(r4);
		
		thr1.start();
		thr2.start();
		thr3.start();
		thr4.start();
		
		try {
			thr1.join();
			thr2.join();
			thr3.join();
			thr4.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		*/
	}

	/*
	 * // Third client Client third = new Client("localhost", port);
	 * third.searchByTopic("horses"); // Fourth Client Client fourth = new
	 * Client("localhost", port); fourth.searchByTopic("systems");
	 * fourth.searchByTopic("college life");
	 */

	private static void runBuy() {
		int iterations = 500;
		Client first = new Client("localhost", port);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			first.buy("57471");
		}
		long executionTime = System.currentTimeMillis() - startTime;
		long averageTime = executionTime / iterations;
		System.out.print("Average Response Time: " + averageTime);
	}

	private static void runSearch() {
		int iterations = 500;
		Client second = new Client("localhost", port);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			second.lookUpById("57471");
		}
		long executionTime = System.currentTimeMillis() - startTime;
		long averageTime = executionTime / iterations;
		System.out.print("Average Response Time: " + averageTime);

	}

}
