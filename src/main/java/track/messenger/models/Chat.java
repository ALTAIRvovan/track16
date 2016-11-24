package track.messenger.models;

import track.messenger.database.DBException;
import track.messenger.store.ChatUserRelationStore;
import track.messenger.store.ChatUserRelationStoreImpl;
import track.messenger.store.StorageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altair on 24.11.16.
 */
public class Chat {
    private long id;

    //not in table
    private List<Long> users = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getUsers() {
        if (users == null) {
            try {
                users = new ArrayList<>();
                ChatUserRelationStore chatUserRelationStore = StorageFactory.getChatUserRelationStore();
                for( Object userId: chatUserRelationStore.getUsersForChat(getId())) {
                    users.add((Long) userId);
                }
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    private void checkUsersArray() {
        if (users == null) {
            users = new ArrayList<>();
        }
    }

    public void addUser(long user) {
        checkUsersArray();
        users.add(user);
    }

    public void addUser(User user) {
        addUser(user.getId());
    }
}
