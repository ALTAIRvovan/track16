package track.messenger.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.messenger.messages.Message;

import java.io.IOException;

/**
 * Created by altair on 23.11.16.
 */
public class SerializeProtocol implements Protocol {
    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, Message.class);
        } catch (IOException e) {
            throw new ProtocolException(e);
        }
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(msg);
        } catch (JsonProcessingException e) {
            throw new ProtocolException(e);
        }
    }
}
