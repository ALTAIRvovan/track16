package track.messenger.net;

import org.apache.log4j.Logger;
import track.messenger.commands.Command;
import track.messenger.commands.CommandException;
import track.messenger.commands.CommandFactory;
import track.messenger.messages.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * Created by altair on 20.11.16.
 */
public class ConnectionManager implements Runnable {

    static Logger logger = Logger.getLogger(ConnectionManager.class);

    private int port;

    private BlockingQueue<Session> outputSessionQueue;

    private Map<SocketChannel, Session> socketChannelSessionMap = new HashMap<>();

    public ConnectionManager(int port, BlockingQueue<Session> outputSessionQueue) {
        this.port = port;
        this.outputSessionQueue = outputSessionQueue;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel = initServerSocketChannel();
        ) {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (!Thread.currentThread().isInterrupted()) {
                int num = selector.select();
                if (num == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    if (key.isAcceptable()) {
                        Socket acceptSocket = serverSocketChannel.socket().accept();
                        SocketChannel socketChannel = acceptSocket.getChannel();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        if (logger.isDebugEnabled()) {
                            logger.debug("New connection localAddr:" + socketChannel.getLocalAddress() +
                                    " remoteAddr:" + socketChannel.getRemoteAddress());
                        }
                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        Session session = getSessionBySocket(socketChannel);
                        if ( key.isValid() && session.isAlive() && socketChannel.isConnected()) {
                            outputSessionQueue.add(session);
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Connection has closed:" + socketChannel.getLocalAddress() +
                                        " remoteAddr:" + socketChannel.getRemoteAddress());
                            }
                            removeSessionToSocket(socketChannel);
                            key.cancel();
                            socketChannel.close();

                        }
                        //processInput(socketChannel);
                        //socketChannel.close();
                    }
                }
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setPort(int port) {
        this.port = port;
    }

    private ServerSocketChannel initServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        logger.info("Socket init on port: " + port);
        return serverSocketChannel;
    }

    private Session addSessionToSocket(SocketChannel socketChannel) {
        Session session = new Session(socketChannel);
        socketChannelSessionMap.put(socketChannel, session);
        return session;
    }

    private void removeSessionToSocket(SocketChannel socketChannel) {
        socketChannelSessionMap.remove(socketChannel);
    }

    private Session getSessionBySocket(SocketChannel socketChannel) {
        Session session = socketChannelSessionMap.get(socketChannel);
        if (session != null) {
            return session;
        }
        return addSessionToSocket(socketChannel);
    }
}
