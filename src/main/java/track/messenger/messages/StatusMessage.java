package track.messenger.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by altair on 23.11.16.
 */
@JsonTypeName("text_message")
public class StatusMessage extends TextMessage {
    public StatusMessage() {
        setType(Type.MSG_STATUS);
    }

    public StatusMessage(String text) {
        this();
        setText(text);
    }
}
