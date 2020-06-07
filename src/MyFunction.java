
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MyFunction {
    static int MIN_DIG_PHONE = 9;
    /*
        this func count the data in the table from DB
    */
    public static int countData(String tableName)
    {
        int total = 0;
        Connection con = MyConnection.getConnection();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS 'total' FROM "+tableName);
            while (rs.next())
            {
                total=rs.getInt(1);
            }
        } catch (SQLException ex) {
            //handleError.showErrorMessage(true, ex.getMessage(), null);
            Logger.getLogger(MyFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }
    
    /*
        return true if the phone number is valid. if no - return false
    */
    public static boolean checkPhoneIsValid(String phone)
    {
        String message;
        if (phone.equals("")) //phone number field is empty
        {
            return true;
        }
        if (phone.length()<MIN_DIG_PHONE && phone.length()>0) //phone number is too short
        {
            message = phone + " isn't a valid phone number ";
            handleError.showErrorMessage(true, message, "Phone Not Valid");
            return false;
        }
        if (phone.charAt(0)!= '0')
        {
            message = "The phone number " + phone + " doesn't start with a '0'!";
            handleError.showErrorMessage(true, message, "Phone Not Valid");
            return false;
        }
        return true;
    }
    
    
    /*
        Finds the p_id of a supplier or a client.
        Parameters are: the c_id/s_id and the type (s for supplier, c for client.
        Returns the p_id. If failed, return -1.   
    */
    public static int findPid(int id,char type) throws SQLException
    {
        String tableName, typeID;
        int p_id= -1;
        Connection con = MyConnection.getConnection();
        Statement st;
        try {
            st = con.createStatement();    
            if (type=='c')
            {
                tableName = "client";
                typeID = "c_id";
            }
            else if (type=='s')
            {
                tableName = "supplier";
                typeID = "s_id";
            }
            else return -1;

            ResultSet rs = st.executeQuery("SELECT p_id FROM " + tableName + " WHERE " + typeID + "=" + String.valueOf(id));
            while (rs.next())
            {
                p_id = rs.getInt(1);
            }
            return p_id;
        } catch (SQLException ex) {
        handleError.showErrorMessage(true, ex.getMessage(), null);
        Logger.getLogger(MyFunction.class.getName()).log(Level.SEVERE, null, ex);
        return -1;
        }
    }
}
