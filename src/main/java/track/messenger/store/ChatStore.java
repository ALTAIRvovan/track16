package track.messenger.store;

import track.messenger.database.DBException;
import track.messenger.models.Chat;

/**
 * Created by altair on 24.11.16.
 */
public interface ChatStore {
    Chat getChat(long chatId);
    Chat createChat(Chat chat) throws DBException;
}
