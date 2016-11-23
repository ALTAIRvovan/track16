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
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties({ "login", "password" })
public class LoginMessage extends FormMessage {

    public LoginMessage() {
        setType(Type.MSG_LOGIN);
    }

    public void setLogin(String value) {
        setField("login", value);
    }

    public String getLogin() {
        return getField("login");
    }

    public void setPassword(String value) {
        setField("password", value);
    }

    public String getPassword() {
        return getField("password");
    }
}
