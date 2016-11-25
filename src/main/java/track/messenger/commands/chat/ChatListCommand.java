package track.messenger.commands.chat;

import track.messenger.commands.Command;
import track.messenger.commands.CommandException;
import track.messenger.database.DBException;
import track.messenger.messages.ChatListMessage;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.models.Chat;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.ChatStore;
import track.messenger.store.StorageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by altair on 25.11.16.
 */
public class ChatListCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        try {
            if (!session.isAutorized()) {
                session.send(new StatusMessage("Вы не авторизированы"));
            }
            List chatIds = StorageFactory.getChatUserRelationStore().getChatsForUser(session.getUser().getId());
            ChatStore chatStore = StorageFactory.getChatStore();
            List<Chat> chats = new ArrayList<>();
            for (Object chatId : chatIds) {
                Chat chat = (Chat) chatStore.getChat((long) chatId);
                chats.add(chat);
            }
            ChatListMessage chatListMessage = new ChatListMessage();
            chatListMessage.setList(chats);
            session.send(chatListMessage);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DBException e) {
            e.printStackTrace();
        }

    }
}
