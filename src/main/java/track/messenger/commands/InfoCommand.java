package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;
import track.messenger.messages.Type;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;

import java.io.IOException;

/**
 * Created by altair on 23.11.16.
 */
public class InfoCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        TextMessage response = new TextMessage();
        response.setType(Type.MSG_INFO_RESULT);
        if (session.isAutorized()) {
            response.setText("Вы авторизированы");
        } else {
            response.setText("Вы не авторизированы");
        }
        try {
            session.send(response);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
