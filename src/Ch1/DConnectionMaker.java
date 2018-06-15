package Ch1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException,
            SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection d = DriverManager.getConnection("jdbc:mysql://localhost/toby?useUnicode=true&useSSL=false&useTimezone=true&serverTimezone=UTC","root",
                "system");
        return d;
    }
}
