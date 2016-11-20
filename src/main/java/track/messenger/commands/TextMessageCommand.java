package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;
import track.messenger.messages.Type;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;

import java.io.IOException;

/**
 * Created by altair on 20.11.16.
 */
public class TextMessageCommand implements Command {

    @Override
    public void execute(Session session, Message message) {
        TextMessage answer = new TextMessage();
        answer.setType(Type.MSG_TEXT);
        answer.setText("HELLO BOY");
        try {
            session.send(answer);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
