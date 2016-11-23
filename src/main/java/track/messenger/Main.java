package track.messenger;

import track.container.Container;
import track.container.JsonConfigReader;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;
import track.messenger.net.MessengerServer;

import java.io.File;
import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        try {
            initContainer();
            MessengerServer messengerServer =
                    (MessengerServer) Container.getInstance().getById("MessengerServer");//new MessengerServer();
            messengerServer.run();
        } catch (IllegalClassFormatException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    private static void initContainer() throws InvalidConfigurationException {
        ConfigReader reader = new JsonConfigReader();
        ClassLoader classLoader = Container.class.getClassLoader();
        File file = new File(classLoader.getResource("messengerConfig.json").getFile());
        Container container = Container.getInstance();
        List<Bean> beans = reader.parseBeans(file);
        container.setBeans(beans);
    }
}
