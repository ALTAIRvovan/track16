package track.messenger.commands.chat;

import track.messenger.commands.Command;
import track.messenger.commands.CommandException;
import track.messenger.database.DBException;
import track.messenger.messages.ChatHistoryMessage;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.TextMessage;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.StorageFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by altair on 25.11.16.
 */
public class ChatHistoryCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        try {
            if (!session.isAutorized()) {
                session.send(new StatusMessage("Вы не авторизированы"));
            }
            //TODO: проверку, что пользователь принадлежит чату
            TextMessage request = (TextMessage)message;
            ChatHistoryMessage response = new ChatHistoryMessage();
            List messages = StorageFactory.getMessageStore().getMessagesFromChat(Long.parseLong(request.getText()));
            response.setList(messages);
            session.send(response);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
