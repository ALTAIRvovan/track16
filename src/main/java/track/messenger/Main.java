package track.messenger;

import track.messenger.net.MessengerServer;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        MessengerServer messengerServer = new MessengerServer();
        messengerServer.run();
    }
}
