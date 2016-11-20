package track.messenger.commands;

import track.messenger.messages.Type;

/**
 * Created by altair on 20.11.16.
 */
public class CommandFactory {



    public static Command getCommand(Type type) throws CommandException {
        switch (type) {
            case MSG_TEXT:
                return new TextMessageCommand();
        }
        throw new CommandException("Command not found");
    }
}
