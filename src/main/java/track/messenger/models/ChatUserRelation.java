package track.messenger.models;

/**
 * Created by altair on 24.11.16.
 */
public class ChatUserRelation {
    private long chatId;
    private long userId;

    public ChatUserRelation() {
    }

    public ChatUserRelation(long chatId, long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
