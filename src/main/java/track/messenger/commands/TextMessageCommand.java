package track.messenger.commands;

import track.messenger.database.DBException;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.TextMessage;
import track.messenger.messages.Type;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.StorageFactory;

import java.io.IOException;

/**
 * Created by altair on 20.11.16.
 */
public class TextMessageCommand implements Command {

    @Override
    public void execute(Session session, Message message) {
        try {
            message.setSenderId(session.getUser().getId());
            TextMessage textMessage = (TextMessage) message;
            try {
                StorageFactory.getMessageStore().addMessage(textMessage.getChatId(), message);
            } catch (DBException e) {
                session.send(new StatusMessage("Ошибка сервера. Повторите поптыку позже"));
                return;
            }
            session.send(new StatusMessage("Сообщение успешно отправлено"));
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
