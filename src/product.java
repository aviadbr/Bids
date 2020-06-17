
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author אביעד
 */
public class product 
{    
    
    public void insertProduct(String product_name, int t_id)
    {
        
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("INSERT INTO product(name, t_id) VALUES (?,?)");
            ps.setString(1, product_name);  
            ps.setInt(2, t_id);  
            if(ps.executeUpdate()>0)
            { 
                    JOptionPane.showMessageDialog(null, "New Product: "+ product_name + " Was Added");
            }                        
        } catch (SQLException ex) {
            handleError.showErrorMessage(true, "Adding was unsuccessful. " + ex.getMessage(), null);
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
    
    public static int findProductID(String product_name)
    {
        Connection con = MyConnection.getConnection();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT *  FROM product WHERE name = '" + product_name +"'");
            while (rs.next())
            {
                    return rs.getInt(1);
            }
        } catch (SQLException ex) {
            handleError.showErrorMessage(true, ex.getMessage(), null);
            Logger.getLogger(MyFunction.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } 
        return -1;
    }



    public static boolean checkProduct(String product_name)
    {
        if (product_name.equals(""))
        {            
            return false;
        }
        if (findProductID(product_name)>=0)
        {
            handleError.showErrorMessage(true, "Product already exist", "");
            return false;
        }

        return true;
    }
}
