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
 * Created by altair on 23.11.16.
 */
public class LoginCommand implements Command {
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
            User user = userStore.getUser(login, password);
            if (user == null) {
                session.send(new StatusMessage("Ошибка авторизации. Не верный логин или пароль"));
                return;
            }
            session.setUser(user);
            session.send(new StatusMessage("Вы успешно авторизировались"));
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
