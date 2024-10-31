package server;


import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.*;
import java.util.concurrent.ConcurrentHashMap;



/**
 * The ServerImpl class implements the ServerInterface
 * and provides methods for storing, retrieving, and deleting key-value pairs
 * using Remote Method Invocation (RMI).
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
  private static final String LOG_FILE = "server-log.txt";
  private Registry registry;

  // Thread-safe map to store key-value pairs
  private static final Map<String, String> store =  new ConcurrentHashMap<>();


  private static final Logger logger = Logger.getLogger(ServerImpl.class.getName());

  /**
   * Constructor for ServerImpl that initializes the server on the specified port.
   *
   * @param port the port number for the server
   * @throws RemoteException if an RMI error occurs
   */
  public ServerImpl(int port, String serviceName) throws RemoteException {
    super();
    this.startServer(port,serviceName);
  }

  @Override
  public String put(String key, String value, String clientName) {
    store.put(key, value);
    log("Client Name - "+clientName+">"+" Success PUT : Key=" + key + ", Value=" + value + " stored.");
    return "Success: Key=" + key + ", Value=" + value + " stored.";
  }

  @Override
  public String get(String key, String clientName) {
    String value = store.get(key);
    if (value != null) {
      log("Client Name - "+clientName+">"+" Success GET : Success: Key=" + key + ", Value=" + value);
      return value;
    } else {
      log("Client Name - "+clientName+">"+" Error GET : Key not found");
      return "Error: Key not found";
    }
  }

  @Override
  public String delete(String key, String clientName) {
    if (store.containsKey(key)) {
      store.remove(key);
      log("Client Name - "+clientName+">"+" Success DELETE : Key=" + key + " deleted.");
      return "Success: Key=" + key + " deleted.";
    } else {
      log("Client Name - "+clientName+">"+" Error DELETE: Key not found");
      return "Error: Key not found";
    }
  }

  /**
   * Initializes and starts the RMI server on the specified port.
   *
   * @param port the port number to bind the server
   * @throws RemoteException if an error occurs during server setup
   */
  private void startServer(int port, String serviceName) throws RemoteException {
    try {
      this.registry = LocateRegistry.createRegistry(port);
      registry.rebind(serviceName, this);
      log(serviceName+" bound in registry on port " + port);
      log("server is running on port " + port + "...");
    } catch (RemoteException e) {
      log("server error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Logs messages to both the console and a log file, including a timestamp.
   *
   * @param message the message to log
   */
  private static void log(String message) {
    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    logger.info("[" + timestamp + "] " + message);
    System.out.println("[" + timestamp + "] " + message);
  }

  static {
    try {
      FileHandler fh = new FileHandler(LOG_FILE, true);
      logger.addHandler(fh);
      logger.setUseParentHandlers(false);
    } catch (IOException e) {
      System.err.println("Failed to initialize logger: " + e.getMessage());
    }
  }

  /**
   * The main method to start the server with the specified command-line arguments.
   *
   * @param args command-line arguments, expects a single port number
   */
  public static void main(String[] args) {
    int port;
    String serviceName;
    if (args.length != 2) {
      log("Incorrect Arguments");
      throw new IllegalArgumentException("Please enter the following arguments - <Port-number> <Server-Name>");
    }
    try {
      port = Integer.parseInt(args[0]);
      serviceName = args[1];

    } catch (NumberFormatException e) {
     log("Invalid port number. Please provide a valid integer.");
      return;
    }
    try {
      new ServerImpl(port,serviceName);
    } catch (RemoteException e) {
      log("Server initialization error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
