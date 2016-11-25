package track.messenger.store;

import track.messenger.database.DBException;
import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;
import track.messenger.messages.Type;

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
        TextMessage textMessage = new TextMessage();
        textMessage.setId(resultSet.getLong("id"));
        textMessage.setSenderId(resultSet.getLong("sender_id"));
        textMessage.setType(Type.MSG_TEXT);
        textMessage.setText(resultSet.getString("text"));
        return textMessage;
    }

    /**
     * Список сообщений из чата
     *
     * @param chatId
     */
    @Override
    public List getMessagesFromChat(Long chatId) {
        return selectObjects("`chat_id`=" + chatId);
    }

    /**
     * Получить информацию о сообщении
     *
     * @param messageId
     */
    @Override
    public Message getMessageById(Long messageId) {
        return (Message)getById(messageId);
    }

    /**
     * Добавить сообщение в чат
     *
     * @param chatId
     * @param message
     */
    @Override
    public void addMessage(Long chatId, Message message) {
        TextMessage textMessage = (TextMessage)message;
        String sql = "(`chat_id`, `sender_id`, `text`) VALUES (" + chatId + ", "
                + message.getSenderId() + ", '"
                + textMessage.getText() + "')";
        insert(sql);
    }
}
