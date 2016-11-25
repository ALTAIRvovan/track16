package track.messenger.net;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
public class MessengerServer {

    private Thread connectionManagerThread;
    private Thread[] workers;

    private int port;
    private static Protocol protocol;
    private int numberWorkers;

    public MessengerServer() {}

    public void run() {
        BlockingQueue<Session> blockingQueue = new LinkedBlockingQueue<>();
        ConnectionManager connectionManager = new ConnectionManager(port, blockingQueue);
        //connectionManager.setPort(10000);
        //ConnectionManager.setProtocol(new SerializeProtocol());
        connectionManagerThread = new Thread(connectionManager);
        connectionManagerThread.start();
        workers = new Thread[numberWorkers];
        for (Thread workerThread: workers) {
            Worker worker = new Worker(blockingQueue, protocol);
            workerThread = new Thread(worker);
            workerThread.start();
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
