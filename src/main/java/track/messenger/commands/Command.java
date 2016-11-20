package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.net.Session;

/**
 * Created by altair on 20.11.16.
 */
public interface Command {

    void execute(Session session, Message message) throws CommandException;
}
