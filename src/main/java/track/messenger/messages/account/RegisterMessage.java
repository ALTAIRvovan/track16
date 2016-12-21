package track.messenger.messages.account;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import track.messenger.messages.FormMessage;
import track.messenger.messages.Type;

/**
 * Created by altair on 23.11.16.
 */
@JsonTypeName("form_message")
public class RegisterMessage extends LoginMessage {

    public RegisterMessage() {
        setType(Type.MSG_REGISTER);
    }
}
