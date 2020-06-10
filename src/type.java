




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
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
public class type {
    
    public void insertType(String typeName)
    {
        
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("INSERT INTO supplier_type(type_name) VALUES (?)");
            ps.setString(1, typeName);     
            if(ps.executeUpdate()>0)
            { 
                    JOptionPane.showMessageDialog(null, "New Type: "+ typeName + " Was Added");
            }                        
        } catch (SQLException ex) {
            handleError.showErrorMessage(true, "Adding was unsuccessful. " + ex.getMessage(), null);
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
    
    public static typeList buildTypeList()
    {
        typeList list = new typeList();
        Connection con = MyConnection.getConnection();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT *  FROM supplier_type");
            while (rs.next())
            {
                list = typeList.insertTypeList(list, rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            handleError.showErrorMessage(true, ex.getMessage(), null);
            Logger.getLogger(MyFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    
    /*
        check if type is a type in DB.
        if yes, return his t_id.
        if no, return -1;
    */
    public static int findTypeID(String type)
    {
        Connection con = MyConnection.getConnection();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT *  FROM supplier_type WHERE type_name = '" + type+"'");
            while (rs.next())
            {
                if(type.equals(rs.getString(2)))
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
        method to fill the combobox with the list of types
    */
    public static void fillTypeComboBox(JComboBox jComboBox_type)
    {
        jComboBox_type.removeAllItems();
        typeList tpl;
        tpl = type.buildTypeList();
        //fill the combolist
        typeList.typeNode node;
        node = tpl.getHead();
        while(node != null)
        {
            jComboBox_type.addItem(node.getName());
            node = node.getNext();
        }       
    }
    
    
    /*
        returns the numbers of types in DB
    */
    public static int getTypeCount()
    {
        int count = 0;
        Connection con = MyConnection.getConnection();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM supplier_type");
            while (rs.next())
            {
                count=rs.getInt(1);
            }
        } catch (SQLException ex) {
            handleError.showErrorMessage(true, ex.getMessage(), null);
            Logger.getLogger(MyFunction.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return count;
    }
}
