package track.messenger.store;

import java.util.List;

/**
 * Created by altair on 24.11.16.
 */
public interface ChatUserRelationStore {
    List getUsersForChat(long chatId);
    List getChatsForUser(long userId);
    long addUserToChat(long chatId, long userId);
    List<Long> addUsersToChat(long chatId, List<Long> users);
}
