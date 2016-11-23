package track.messenger.store;

import org.apache.log4j.Logger;
import track.messenger.models.User;
import track.messenger.database.DBException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by altair on 20.11.16.
 */
public class UserStoreImpl extends AbstractStorage<User> implements UserStore {

    public UserStoreImpl() throws DBException {
        super();
        logger = Logger.getLogger(UserStoreImpl.class);
        dbTable = "users";
    }

    @Override
    public User getObjectFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    /**
     * Добавить пользователя в хранилище
     * Вернуть его же
     *
     * @param user
     */
    @Override
    public User addUser(User user) {
        String cond = "(`login`, `password`) VALUES (" + user.getLogin() + ", " + user.getPassword() + ")";
        return insert(cond);
    }

    /**
     * Обновить информацию о пользователе
     *
     * @param user
     */
    @Override
    public User updateUser(User user) {
        String setCond = "`login`=" + user.getLogin() + ", `password`=" + user.getPassword();
        String whereCond = "`id`=" + user.getId();
        update(setCond, whereCond);
        return user;
    }

    /**
     * Получить пользователя по логину/паролю
     * return null if user not found
     *
     * @param login
     * @param pass
     */
    @Override
    public User getUser(String login, String pass) {
        List userList = selectObjects("`login` = '" + login + "'");
        for (Object user: userList) {
            if ( ((User) user).checkPassword(pass)) {
                return (User) user;
            }
        }
        return null;
    }

    /**
     * Получить пользователя по id, например запрос информации/профиля
     * return null if user not found
     *
     * @param id
     */
    @Override
    public User getUserById(Long id) {
        return (User) selectObject("`id`=" + id);
    }
}
