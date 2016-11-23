package track.messenger.net;

/**
 *
 */
public class MessengerServer {

    public void run() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.setPort(10000);
        ConnectionManager.setProtocol(new SerializeProtocol());
        Thread connectionManagerThread = new Thread(connectionManager);
        connectionManagerThread.start();
    }
}
