
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;


/**
 * Created by Poojan on 4/19/2017.
 */
public class ZookeeperLocal {

    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperLocal.class);
    private ServerConfig config = new ServerConfig();
    private ZooKeeperServerMain zooKeeperServer;
    Properties properties = new Properties();

    public ZookeeperLocal(){

        properties.setProperty("dataDir","target/logs.zookeper/");
        properties.setProperty("clientPort","2183");
        QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
        try {
            quorumConfiguration.parseProperties(properties);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        zooKeeperServer = new ZooKeeperServerMain();
        final ServerConfig configuration = new ServerConfig();
        configuration.readFrom(quorumConfiguration);

        new Thread() {
            public void run() {
                try {
                    zooKeeperServer.runFromConfig(configuration);
                } catch (IOException e) {
                    LOG.error("ZooKeeper Failed",e.getStackTrace());
                    System.out.println(e.getStackTrace());
                }
            LOG.info("Zookeeper Started");
            }
        }.start();
    }
}
