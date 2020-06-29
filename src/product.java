
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
    int NUMBER_OF_COLUMNS_PRODUCT = 3;
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
    
    public void updateProduct(String product_name, int t_id, int pr_id)
    {
        
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("UPDATE `product` SET `name`=?,`t_id`=? WHERE `pr_id`=?");
            ps.setString(1, product_name);  
            ps.setInt(2, t_id);
            ps.setInt(3, pr_id);
            if(ps.executeUpdate()>0)
            { 
                    JOptionPane.showMessageDialog(null, "Product: "+ product_name + " Updated Successfully");
            }                        
        } catch (SQLException ex) {
            handleError.showErrorMessage(true, "Updating was unsuccessful. " + ex.getMessage(), null);
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
    
    /*
        Removes pr_id product from the DB.
    */
    public void deleteProduct(Integer pr_id)
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        
        try {
                ps = con.prepareStatement("DELETE FROM `product` WHERE `pr_id` = ?"); 
                ps.setInt(1, pr_id);
                
                if(ps.executeUpdate()>0)
                { 
                    JOptionPane.showMessageDialog(null, "Product Deleted Successfully");
                }                                      
            } catch (SQLException ex) {
                handleError.showErrorMessage(true, "Deleting was unsuccessful. " + ex.getMessage(), null);
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


    /*
        Checks if the product is valid (return true).
        If no, return false.
    */
    public static boolean checkProduct(String product_name,int t_id, boolean newProduct)
    {
        if (product_name.equals(""))
        {            
            return false;
        }
        if (newProduct == true && findProductID(product_name)>=0)
        {
            handleError.showErrorMessage(true, "Product already exist", "");
            return false;
        }
        if (t_id < 0)
        {
            handleError.showErrorMessage(true, "Type is not exist", "");
            return false;
        }
        return true;
    }
    
    
     /*
        fill the table with the supplier list
    */
    public void fillProductJTable(JTable table, String valueToSearch)
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("SELECT * FROM `product` NATURAL JOIN type WHERE CONCAT(`pr_id`,`name`,`type_name`) LIKE ?");
            ps.setString(1,"%" +valueToSearch+ "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            Object[] row;
            while(rs.next())
            {
                row=new Object[NUMBER_OF_COLUMNS_PRODUCT];
                row[0] = rs.getInt(2);  //p_id
                row[1] = rs.getString(3); //product name
                row[2] = rs.getString(4); //type_name      
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillProductTableAgain(String searchValue)
    {
        DefaultTableModel tableModel = new DefaultTableModel(null, new Object[]{"ID","Product Name","Type"}) {

        @Override
        public boolean isCellEditable(int row, int column) {
           //all cells false
           return false;
        }
        };
        manageProductsForm.tbl_product.setModel(tableModel);
        fillProductJTable(manageProductsForm.tbl_product, searchValue);
    }
}
