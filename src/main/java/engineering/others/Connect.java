package engineering.others;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {

    private String jdbc;
    private String user;
    private String password;
    private static Connect instance = null;
    private Connection conn = null;

    private static final String PATH = "src/main/resources/connection.properties";

    protected Connect() {
    }

    public static synchronized Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    public synchronized Connection getDBConnection() throws SQLException {
        if (this.conn == null) {
            getInfo();
            this.conn = DriverManager.getConnection(jdbc, user, password);
        }
        return this.conn;
    }

    private void getInfo() {
        try(FileInputStream fileInputStream = new FileInputStream(PATH)) {
            // Load DB Connection info from Properties file
            Properties prop = new Properties() ;
            prop.load(fileInputStream);

            jdbc = prop.getProperty("JDBC_URL") ;
            user = prop.getProperty("USER") ;
            password = prop.getProperty("PASSWORD") ;

        } catch (IOException e){
            e.fillInStackTrace();
        }
    }

}