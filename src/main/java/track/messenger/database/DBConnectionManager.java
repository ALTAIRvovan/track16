package track.messenger.database;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by altair on 23.11.16.
 */
public class DBConnectionManager {

    private ConnectionPoolDataSource poolDataSource;

    public Connection getConnection() throws SQLException {
        return poolDataSource.getPooledConnection().getConnection();
    }

    public void setPoolDataSource(ConnectionPoolDataSource poolDataSource) {
        this.poolDataSource = poolDataSource;
    }

}
