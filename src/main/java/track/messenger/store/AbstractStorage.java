package track.messenger.store;

import org.apache.log4j.Logger;
import track.container.Container;
import track.messenger.database.DBConnectionManager;
import track.messenger.database.DBException;

import java.lang.instrument.IllegalClassFormatException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by altair on 23.11.16.
 */
public abstract class AbstractStorage<T> {

    protected Logger logger;

    public String dbTable;

    protected DBConnectionManager connectionManager;

    public AbstractStorage() throws DBException {
        try {
            connectionManager = (DBConnectionManager) Container.getInstance().getById("DBConnectionManager");

        } catch (IllegalClassFormatException e) {
            throw new DBException("Не удалось создать connectionManager", e);
        }
    }

    public ResultSet execUpdate(String sql) throws SQLException {
        try (Connection con = connectionManager.getConnection();) {
            Statement statement = con.createStatement();
            if (statement.executeUpdate(sql) > 0) {
                return statement.getGeneratedKeys();
            }
            return null;
        }
    }

    public ResultSet execQuery(String sql) throws SQLException {
        try (Connection con = connectionManager.getConnection();) {
            Statement statement = con.createStatement();
            return statement.executeQuery(sql);
        }
    }

    public boolean exec(String sql) throws SQLException {
        try (Connection con = connectionManager.getConnection();) {
            Statement statement = con.createStatement();
            return statement.execute(sql);
        }
    }

    public Object select(String whereCond, Function<ResultSet, Object> func) {
        //Сюда бы еще проверку типа для объектов условия. Защиту от SQL инъекций
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM `").append(dbTable).append("`");
        if (whereCond != null) {
            sql.append("WHERE ").append(whereCond);
        }
        ResultSet resultSet = null;
        try {
            resultSet = execQuery(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return func.apply(resultSet);
    }

    public List selectObjects(String whereCond) {
        return (List) select(whereCond, this::getObjectListFromResultSet);
    }

    public List<T> getObjectListFromResultSet(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet != null && resultSet.next()) {
                list.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Object selectObject(String whereCond) {
        return select(whereCond, (resultSet) -> {
            try {
                if (resultSet.next()) {
                    return getObjectFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                logger.error("Не удалось создать объект", e);
            }
            return null;
        });
    }

    public Object getById(long id) {
        return selectObject("`id`=" + id);
    }

    public List<Long> insert(String cond) {
        String sql = "INSERT INTO `" + dbTable + "`" + cond;
        Logger.getLogger(AbstractStorage.class).info(sql);
        List<Long> list = new ArrayList<>();
        try {
            ResultSet resultSet = execUpdate(sql);
            while (resultSet != null && resultSet.next()) {
                list.add(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ResultSet update(String setCond, String whereCond) {
        String sql = "UPDATE `" + dbTable + "` SET " + setCond + " WHERE " + whereCond;
        try {
            return execUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract T getObjectFromResultSet(ResultSet resultSet) throws SQLException;
}
