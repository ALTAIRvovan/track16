package track.messenger.store;

import track.messenger.database.DBException;
import track.messenger.models.Chat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by altair on 24.11.16.
 */
public class ChatStoreImpl extends AbstractStorage<Chat> implements ChatStore {

    public ChatStoreImpl() throws DBException {
    }

    @Override
    public Chat getObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Chat chat = new Chat();
        chat.setId(resultSet.getLong("id"));
//        String sql = "SELECT `user_id` FROM `" + dbChatUserTable + "` WHERE `chat_id` = " + chat.getId();
//        ResultSet resultSet1 = execQuery(sql);
//        while (resultSet1 != null && resultSet1.next()) {
//            chat.addUser(resultSet.getLong("user_id"));
//        }
        //chat.setUsers(chatUserRelationStoreImpl.getUsersForChat(chat.getId()));
        return chat;
    }

    public Chat getChat(long chatId) {
        return (Chat) getById(chatId);
    }

    public Chat createChat(Chat chat) throws DBException {
        List<Long> listIndexes = insert("(`name`) VALUES ('new_chat')");
        if (listIndexes.isEmpty()) {
            throw new DBException("Chat can not be created");
        }
        chat.setId(listIndexes.get(0));
        StorageFactory.getChatUserRelationStore().addUsersToChat(chat.getId(), chat.getUsers());
        return chat;
    }
}
