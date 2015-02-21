import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

  public static void main(String[] args) {
    String host = (args.length < 1) ? "localhost" : args[0];
    try {
      Registry registry = LocateRegistry.getRegistry(host, 8888);
      ClientToFronEndServer stub = (ClientToFronEndServer) registry.lookup("FrontEndServer");
     /* String response = stub.sayHello();
      System.out.println("response: " + response);*/
    } catch (Exception e) {
        System.err.println("Client exception: " + e.toString());
    }
  }

}
