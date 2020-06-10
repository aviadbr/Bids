



import java.sql.Connection;
import java.sql.DriverManager;

        
public class MyConnection {
    public static Connection getConnection()
    {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/bidsdb","root","");
        } catch (Exception ex) {
            handleError.showErrorMessage(true, ex.getMessage(), null);
            System.out.println(ex.getMessage());
            
        }
        return con;
    }
}
