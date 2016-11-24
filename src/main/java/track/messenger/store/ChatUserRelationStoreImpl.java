package track.messenger.store;

import track.messenger.database.DBException;
import track.messenger.models.ChatUserRelation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by altair on 24.11.16.
 */
public class ChatUserRelationStoreImpl extends AbstractStorage implements ChatUserRelationStore {

    public ChatUserRelationStoreImpl() throws DBException {
        //this.dbTable = "link_chat_user";
    }

    @Override
    public Object getObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new ChatUserRelation(resultSet.getLong("chat_id"), resultSet.getLong("user_id"));
    }

    @Override
    public List getUsersForChat(long chatId) {
        String sql = "`chat_id`=" + chatId;
        return (List) (select(sql, (resultSet1) -> {
            ResultSet resultSet = (ResultSet) resultSet1;//Костыль, по другому не компилировалось
            List<Long> list = new ArrayList<>();
            try {
                while (resultSet != null && resultSet.next()) {
                    list.add(resultSet.getLong("user_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return (Object) list;
        }));
    }

    @Override
    public List getChatsForUser(long userId) {
        return (List) select("`user_id`=" + userId, (resultSet1) -> {
            ResultSet resultSet = (ResultSet) resultSet1;
            List<Long> list = new ArrayList<>();
            try {
                while (resultSet != null && resultSet.next()) {
                    list.add(resultSet.getLong("chat_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        });
    }

    @Override
    public long addUserToChat(long chatId, long userId) {
        String cond = ("(`chat_id`, `user_id`) VALUES (" + chatId + ", " + userId + ")");
        return (long)((List) insert(cond)).get(0);
    }

    @Override
    public List<Long> addUsersToChat(long chatId, List<Long> users) {
        List<Long> chatUserRelations = new ArrayList<>();
        for (long userId : users) {
            chatUserRelations.add(addUserToChat(chatId, userId));
        }
        return chatUserRelations;
    }
}
