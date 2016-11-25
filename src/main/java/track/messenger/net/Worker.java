package track.messenger.net;

import track.messenger.messages.Message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

/**
 * Created by altair on 25.11.16.
 */
public class Worker implements Runnable {

    private BlockingQueue<Session> sessionQueue;
    private Protocol protocol;

    public Worker(BlockingQueue sessionQueue, Protocol protocol) {
        this.sessionQueue = sessionQueue;
        this.protocol = protocol;
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(128);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Session session = sessionQueue.take();
                int numberOfBytes = session.getSocket().read(buffer);
                if (numberOfBytes > 0) {
                    try {
                        buffer.rewind();
                        Message message = protocol.decode(buffer.array());
                        session.onMessage(message);
                        //buffer.rewind();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                    //socketChannel.write(buffer);
                    buffer.clear();
                } else if (numberOfBytes < 0) {
                    session.kill();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
