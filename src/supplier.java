



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author אביעד
 */
public class supplier {
    
    int NUMBER_OF_COLUMNS_SUPPLIER = 10;
    /*
        insert a supplier to DB
    */
    public void insertSupplier(String fname, String lname,
            String mphone, String hphone, String address, boolean isActive, String comments, String dateAdded, int t_id)
    {
        
        Connection con = MyConnection.getConnection();
        PreparedStatement ps1, ps2;
        
        try {
            ps1 = con.prepareStatement("INSERT INTO person(first_name,"
                    + " last_name, m_phone, h_phone, date_added, address, comments) VALUES (?,?,?,?,?,?,?)");
            ps1.setString(1, fname);
            ps1.setString(2, lname);
            ps1.setString(3, mphone);
            ps1.setString(4, hphone);
            ps1.setString(5, dateAdded);
            ps1.setString(6, address);
            ps1.setString(7, comments);     

            if(ps1.executeUpdate()>0) //if execute  succeeded.
            { 
                ps2 = con.prepareStatement("INSERT INTO supplier(p_id,t_id,isActive) VALUES (LAST_INSERT_ID(),?,?)");
                ps2.setInt(1, t_id);
                ps2.setBoolean(2, isActive);
                if(ps2.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null, "New Supplier Was Added");
                }
            }          

        } catch (SQLException ex) {
            handleError.showErrorMessage(true, "Adding was unsuccessful. " + ex.getMessage(), null);
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    /*
        Update a supplier from DB
        Param - Integers: s_id, t_id
                Strings: fname,lname,mphone,hphone,address,comments
                Boolean: isActive
    */
    public void updateSupplier(Integer s_id, String fname, String lname,
            String mphone, String hphone, String address, String comments,boolean isActive, int t_id)
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps1, ps2;
        int p_id;
        try {
            p_id = MyFunction.findPid(Integer.valueOf(s_id), 's');
            ps1 = con.prepareStatement("UPDATE `person` SET `first_name`=?,`last_name`=?,"
                    + "`m_phone`=?,`h_phone`=?,`address`=?,`comments`=? WHERE `p_id` = ?");
            ps1.setString(1, fname);
            ps1.setString(2, lname);
            ps1.setString(3, mphone);
            ps1.setString(4, hphone);
            ps1.setString(5, address);
            ps1.setString(6, comments);     
            ps1.setInt(7, p_id);

            if(ps1.executeUpdate()>0)
            { 
                ps2 = con.prepareStatement("UPDATE `supplier` SET `t_id`=?,`isActive`=? WHERE `s_id`= ?");
                ps2.setInt(1, t_id);
                ps2.setBoolean(2, isActive);
                ps2.setInt(3, s_id);
                if(ps2.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null, "Supplier Data Updated");
                }
                else
                {
                    handleError.showErrorMessage(true, "Updating was unsuccessful.", null);
                }
            }                                      
        } catch (SQLException ex) {
            handleError.showErrorMessage(true, "Updating was unsuccessful." + ex.getMessage(), null);
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }               
    }
    
    /*
        Delete a supplier from DB
    */
    public void deleteSupplier(Integer s_id)
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        
        try {
                ps = con.prepareStatement("DELETE FROM `supplier` WHERE `s_id` = ?"); 
                ps.setInt(1, s_id);
                
                if(ps.executeUpdate()>0)
                { 
                    JOptionPane.showMessageDialog(null, "Supplier Deleted");
                    return;
                }                                      
            } catch (SQLException ex) {
                handleError.showErrorMessage(true, "Deleting was unsuccessful. " + ex.getMessage(), null);
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }        
    }
    
    /*
        fill the table with the supplier list
    */
    public void fillSupplierJTable(JTable table, String valueToSearch)
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("SELECT * FROM `person` NATURAL JOIN supplier NATURAL JOIN supplier_type WHERE CONCAT('c_id',`first_name`,`last_name`,`m_phone`,`h_phone`,`date_added`,`address`,`comments`,'isActive','type_name') LIKE ?");
            ps.setString(1,'%' +valueToSearch+ '%');
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            Object[] row;
            while(rs.next())
            {
                row=new Object[NUMBER_OF_COLUMNS_SUPPLIER];
                row[0] = rs.getInt(10);  //c_id pos is 9
                row[1] = rs.getString(3); //fname
                row[2] = rs.getString(4); //lname
                row[3] = rs.getString(5); //mphone
                row[4] = rs.getString(6);//hphone
                row[5] = rs.getString(7); //data added
                row[6] = rs.getString(8); //address
                row[7] = rs.getString(9); //comments
                row[8] = rs.getString(12); //comments
                if (rs.getBoolean(11) == true) //is active                
                    row[9] = "Yes";
                else
                    row[9] = "No";            
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillSupplierTableAgain(String searchValue)
    {
        DefaultTableModel tableModel = new DefaultTableModel(null, new Object[]{"ID","First Name","First Name",
            "Mobile Phone","Home Phone","Date Added","Address","Comments","Type","Is Active"}) {

        @Override
        public boolean isCellEditable(int row, int column) {
           //all cells false
           return false;
        }
    };


        manageSupplierForm.tbl_supplier.setModel(tableModel);
        fillSupplierJTable(manageSupplierForm.tbl_supplier, searchValue);
    }
    
    /*
        Methid to check the data for insert/update supplier.
        If valid, returns true. Else returns false.
    */
    public boolean checkSupplier(String fname, String lname, String mphone, String hphone, int t_id)
    {
        if ((fname.equals("")) && (lname.equals(""))) //if no name was given
        {
            handleError.showErrorMessage(true, "Please give a name for the supplier", "No Name");
            return false;
        }
        
        if ((MyFunction.checkPhoneIsValid(mphone)==false) || (MyFunction.checkPhoneIsValid(hphone)==false))
            return false;
        
        if (t_id<0)
        {
            handleError.showErrorMessage(true, "Type doesn't exist", "Wrong Type");
            return false;
        }
        return true;
    }
}
