package track.messenger.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import track.messenger.User;
import track.messenger.commands.Command;
import track.messenger.commands.CommandException;
import track.messenger.commands.CommandFactory;
import track.messenger.messages.Message;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session {

    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private User user;

    // сокет на клиента
    private SocketChannel socket;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
    private InputStream in;
    private OutputStream out;

    public Session(SocketChannel socket) {
        this.socket = socket;
    }

    public void send(Message msg) throws ProtocolException, IOException {
        byte[] bytes = ConnectionManager.getProtocol().encode(msg);
        socket.write(ByteBuffer.wrap(bytes));
    }

    public void onMessage(Message message) {
        Command command = null;
        try {
            command = CommandFactory.getCommand(message.getType());
            command.execute(this, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }
}