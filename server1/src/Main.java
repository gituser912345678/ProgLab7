import collectionManager.CollectionManager;
import dbManager.DbManager;
import network.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        DbManager dbManager = new DbManager();
        CollectionManager.setMap(dbManager.createMap());
        server.start();
    }
}