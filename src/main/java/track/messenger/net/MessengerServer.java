package track.messenger.net;

import javassist.bytecode.analysis.*;

import java.util.concurrent.*;

/**
 *
 */
public class MessengerServer {

    private Thread connectionManagerThread;
    private ExecutorService workersService;

    private int port;
    private static Protocol protocol;
    private int numberWorkers;
    private SessionStorage sessionStorage;
    private int queueCapacity;

    public MessengerServer() {}

    public void run() {
        BlockingQueue<Session> blockingQueue = new ArrayBlockingQueue<Session>(queueCapacity);
        ConnectionManager connectionManager = new ConnectionManager(port, blockingQueue, sessionStorage);
        connectionManagerThread = new Thread(connectionManager);
        connectionManagerThread.start();
        workersService = Executors.newFixedThreadPool(numberWorkers);
        for (int i = 0; i < numberWorkers; i++) {
            workersService.submit(new Worker(blockingQueue, protocol));
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static Protocol getProtocol() {
        return protocol;
    }

    public static void setProtocol(Protocol protocol) {
        MessengerServer.protocol = protocol;
    }

    public int getNumberWorkers() {
        return numberWorkers;
    }

    public void setNumberWorkers(int numberWorkers) {
        this.numberWorkers = numberWorkers;
    }
}
