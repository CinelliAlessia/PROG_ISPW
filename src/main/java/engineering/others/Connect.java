package engineering.others;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connect instance = null;
    private Connection conn = null;

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
            this.conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        }
        return this.conn;
    }

}