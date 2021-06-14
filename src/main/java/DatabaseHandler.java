import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler extends Configs{

    //  Database credentials
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "177MrDeer";
    private static final String DB_HOST = "localhost:3306";
    private static final String DB_NAME = "EPAM_project";

    public static void connectToDB() {
        Connection con = null;

        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + "/" + DB_NAME + "?characterEncoding=utf8",
                    DB_USER,
                    DB_PASSWORD);
            System.out.println("ОК");
        } catch (ClassNotFoundException |
                SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
