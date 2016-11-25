package track.messenger.net;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by altair on 25.11.16.
 */
public class SessionStorageImpl implements SessionStorage {

    private Map<SocketChannel, Session> socketChannelSessionMap = new HashMap<>();

    public Session addSessionToSocket(SocketChannel socketChannel) {
        Session session = new Session(socketChannel);
        socketChannelSessionMap.put(socketChannel, session);
        return session;
    }

    public void removeSessionToSocket(SocketChannel socketChannel) {
        socketChannelSessionMap.remove(socketChannel);
    }

    public Session getSessionBySocket(SocketChannel socketChannel) {
        Session session = socketChannelSessionMap.get(socketChannel);
        if (session != null) {
            return session;
        }
        return addSessionToSocket(socketChannel);
    }
}
