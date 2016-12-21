package track.messenger.commands.accaunts;

import track.messenger.commands.Command;
import track.messenger.commands.CommandException;
import track.messenger.database.DBException;
import track.messenger.messages.FormMessage;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.models.User;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.UserStoreImpl;

import java.io.IOException;

/**
 * Created by altair on 21.12.16.
 */
public class RegisterCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        try {
            if (session.isAutorized()) {
                session.send(new StatusMessage("Вы уже авторизированы"));
                return;
            }
            if (!(message instanceof FormMessage)) {
                throw new CommandException("Ошибка типа сообщения");
            }
            FormMessage formMessage = (FormMessage) message;
            String login = formMessage.getField("login");
            String password = formMessage.getField("password");
            UserStoreImpl userStore = new UserStoreImpl();
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            userStore.addUser(user);
            if (user.isAutorized()) {
                session.setUser(user);
                session.send(new StatusMessage("Вы успешно зарегистированы и авторизированы"));
            } else {
                session.send(new StatusMessage("Ошибка регистрации"));
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
