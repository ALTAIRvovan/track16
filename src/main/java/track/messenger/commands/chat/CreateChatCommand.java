package track.messenger.commands.chat;

import track.messenger.commands.Command;
import track.messenger.commands.CommandException;
import track.messenger.database.DBException;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.TextMessage;
import track.messenger.models.Chat;
import track.messenger.models.User;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.StorageFactory;

import java.io.IOException;

/**
 * Created by altair on 24.11.16.
 */
public class CreateChatCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        try {
            if (!session.isAutorized()) {
                session.send(new StatusMessage("Вы не авторизированы"));
                return;
            }
            TextMessage msg = (TextMessage) message;
            String[] users = msg.getText().split(",");
            Chat chat = new Chat();
            chat.addUser(session.getUser().getId());
            for (String user : users) {
                chat.addUser(Long.parseLong(user));
            }
            chat = StorageFactory.getChatStore().createChat(chat);
            session.send(new StatusMessage("Чат создан chatId:" + chat.getId()));
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
