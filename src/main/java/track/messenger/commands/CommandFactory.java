package track.messenger.commands;

import track.messenger.commands.accaunts.LoginCommand;
import track.messenger.commands.chat.ChatHistoryCommand;
import track.messenger.commands.chat.ChatListCommand;
import track.messenger.commands.chat.CreateChatCommand;
import track.messenger.messages.Type;

/**
 * Created by altair on 20.11.16.
 */
public class CommandFactory {



    public static Command getCommand(Type type) throws CommandException {
        switch (type) {
            case MSG_TEXT:
                return new TextMessageCommand();
            case MSG_INFO:
                return new InfoCommand();
            case MSG_LOGIN:
                return new LoginCommand();
            case MSG_CHAT_CREATE:
                return new CreateChatCommand();
            case MSG_CHAT_LIST:
                return new ChatListCommand();
            case MSG_CHAT_HIST:
                return new ChatHistoryCommand();
        }
        throw new CommandException("Command not found");
    }
}
