package track.messenger.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 *
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextMessage.class, name = "text_message"),
        @JsonSubTypes.Type(value = FormMessage.class, name = "form_message"),
        @JsonSubTypes.Type(value = ListMessage.class, name = "list_message")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Message implements Serializable {

    private Long id;
    private Long senderId;
    private Type type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
