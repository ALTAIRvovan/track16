package track.messenger;

import track.container.Container;
import track.container.JsonConfigReader;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;
import track.messenger.database.DBConnectionManager;
import track.messenger.net.MessengerServer;

import javax.sql.ConnectionPoolDataSource;
import java.io.File;
import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        initContainer();
        MessengerServer messengerServer = new MessengerServer();
        messengerServer.run();
    }

    private static void initContainer() {
        ConfigReader reader = new JsonConfigReader();
        ClassLoader classLoader = Container.class.getClassLoader();
        File file = new File(classLoader.getResource("messengerConfig.json").getFile());
        Container container = Container.getInstance();
        try {
            List<Bean> beans = reader.parseBeans(file);
            container.setBeans(beans);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            DBConnectionManager connectionManager = (DBConnectionManager) Container.getInstance().getById("DBConnectionManager");
            System.out.print(connectionManager.toString());
        } catch (IllegalClassFormatException e) {
            e.printStackTrace();
        }
    }
}
