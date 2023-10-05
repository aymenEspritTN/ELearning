
import com.deltaVelorum.coursify.DatabaseConnection;

import java.sql.Connection;
public class Main {
    public Connection cnx;
    public static void main(String[] args)
    {
        DatabaseConnection.getInstance().getCnx();
        com.deltaVelorum.coursify.chapitre.Test.testGetAll();
    }
}

