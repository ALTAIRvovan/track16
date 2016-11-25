package track.messenger.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by altair on 25.11.16.
 */
@JsonTypeName("chat_history_message")
public class ChatHistoryMessage extends ListMessage<TextMessage> {
    public ChatHistoryMessage() {
        setType(Type.MSG_CHAT_HIST_RESULT);
    }
}
