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


		//Start 2 Thread Test
		System.out.println("Start 2 Thread Test");
		Thread thr1 = new Thread(r1);
		Thread thr2 = new Thread(r1);

		
		thr1.start();
		thr2.start();
	
		
		try {
			thr1.join();
			thr2.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Start 4 Thread Test
		System.out.println("Start 4 Thread Test");
		Thread thrA = new Thread(r1);
		Thread thrB = new Thread(r1);
		Thread thrC = new Thread(r1);
		Thread thrD = new Thread(r1);
		
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
	
	
	//Start many Thread Test
	System.out.println("Start Many Thread Test");
	int threadCount = 50;
	Thread[] threads = new Thread[threadCount];
	for(int i = 0; i < threadCount; i++){
		threads[i] = new Thread(r1);
	}
	
	for(int i = 0; i < threadCount; i++){
		threads[i].start();
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
		System.out.println("Average Buy Response Time: " + averageTime);
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
		System.out.println("Average Search Response Time : " + averageTime);

	}

}
