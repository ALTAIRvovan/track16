package track.messenger.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import track.messenger.models.User;
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

    private boolean alive = true;


    public Session(SocketChannel socket) {
        this.socket = socket;
    }

    public void send(Message msg) throws ProtocolException, IOException {
        byte[] bytes = MessengerServer.getProtocol().encode(msg);
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

    public boolean isAutorized() {
        return user != null && user.isAutorized();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;
    }
}