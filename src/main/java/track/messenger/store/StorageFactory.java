package track.messenger.store;

import track.container.Container;
import track.messenger.database.DBException;

import java.lang.instrument.IllegalClassFormatException;

/**
 * Created by altair on 24.11.16.
 */
public class StorageFactory {
    private static ChatStore chatStore;
    private static UserStore userStore;
    private static ChatUserRelationStore chatUserRelationStore;

    public static ChatStore getChatStore() throws DBException {
        if (chatStore == null) {
            //throw new DBException("ChatStorage Not Set");
            try {
                chatStore = (ChatStore) Container.getInstance().getById("ChatStore");
            } catch (IllegalClassFormatException e) {
                throw new DBException("ChatStorage Not Set",e);
            }
        }
        return chatStore;
    }

    public static UserStore getUserStore() throws DBException {
        if (userStore == null) {
            try {
                userStore = (UserStore) Container.getInstance().getById("UserStore");
            } catch (IllegalClassFormatException e) {
                throw new DBException("UserStorage Not Set", e);
            }
        }
        return userStore;
    }

    public static ChatUserRelationStore getChatUserRelationStore() throws DBException {
        if (chatUserRelationStore == null) {
            try {
                chatUserRelationStore = (ChatUserRelationStore) Container.getInstance().getById("ChatUserRelationStore");
            } catch (IllegalClassFormatException e) {
                throw new DBException("ChatUserRelationStore not set", e);
            }
        }
        return chatUserRelationStore;
    }

    public static void setChatStore(ChatStore chatStore) {
        StorageFactory.chatStore = chatStore;
    }

    public static void setUserStore(UserStore userStore) {
        StorageFactory.userStore = userStore;
    }

    public static void setChatUserRelationStore(ChatUserRelationStore chatUserRelationStore) {
        StorageFactory.chatUserRelationStore = chatUserRelationStore;
    }
}
