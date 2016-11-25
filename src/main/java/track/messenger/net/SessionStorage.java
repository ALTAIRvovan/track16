package track.messenger.net;

import java.nio.channels.SocketChannel;

/**
 * Created by altair on 25.11.16.
 */
public interface SessionStorage {
    Session addSessionToSocket(SocketChannel socketChannel);
    void removeSessionToSocket(SocketChannel socketChannel);
    Session getSessionBySocket(SocketChannel socketChannel);
}
