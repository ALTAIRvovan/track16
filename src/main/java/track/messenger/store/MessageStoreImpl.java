package track.messenger.store;

import track.messenger.database.DBException;
import track.messenger.messages.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by altair on 24.11.16.
 */
public class MessageStoreImpl extends AbstractStorage<Message> implements MessageStore{

    public MessageStoreImpl() throws DBException {

    }

    @Override
    public Message getObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    /**
     * Список сообщений из чата
     *
     * @param chatId
     */
    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        return null;
    }

    /**
     * Получить информацию о сообщении
     *
     * @param messageId
     */
    @Override
    public Message getMessageById(Long messageId) {
        return null;
    }

    /**
     * Добавить сообщение в чат
     *
     * @param chatId
     * @param message
     */
    @Override
    public void addMessage(Long chatId, Message message) {

    }
}
