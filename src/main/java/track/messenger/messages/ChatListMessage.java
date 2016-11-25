package track.messenger.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import track.messenger.models.Chat;

/**
 * Created by altair on 25.11.16.
 */
@JsonTypeName("chat_list_message")
public class ChatListMessage extends ListMessage<Chat> {

    public ChatListMessage() {
        setType(Type.MSG_CHAT_HIST_RESULT);
    }
}
