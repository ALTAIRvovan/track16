package track.messenger.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.List;

/**
 * Created by altair on 25.11.16.
 */
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChatListMessage.class, name = "chat_list_message"),
        @JsonSubTypes.Type(value = ChatHistoryMessage.class, name = "chat_history_message"),
})
public abstract class ListMessage<T> extends Message {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
