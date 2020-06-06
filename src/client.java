
import java.sql.Connection;
import java.sql.Date;
import java.lang.Integer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.UIManager.get;
import javax.swing.table.DefaultTableModel;

public class client {
    static int NUMBER_OF_ROWS_CLIENT = 8;
    public void insertUpdateDeleteClient(char operation, Integer id, String fname, String lname,
            String address, String mphone, String hphone, String comments, String dateAdded)
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps1, ps2;
        
        // i for insert
        if (operation == 'i')
        {
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
            
                int valid1,valid2;
                valid1 = ps1.executeUpdate();
                if(valid1>0)
                { 
                    ps2 = con.prepareStatement("INSERT INTO client(p_id) VALUES (LAST_INSERT_ID())");
                    valid2 = ps2.executeUpdate();
                    if(valid2>0)
                    {
                        JOptionPane.showMessageDialog(null, "New Client Was Added");
                        return;
                    }
                }          
                
            } catch (SQLException ex) {
                handleError.showErrorMessage(true, "Adding was unsuccessful." + ex.getMessage(), null);
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }    
            
        }
    }
    
    public void fillClientJTable(JTable table, String valueToSearch)
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("SELECT * FROM `client` NATURAL JOIN person "
                    + "         WHERE CONCAT('first_name','last_name','address','m_phone','h_phone','date_added','comments')LIKE ?");
            ps.setString(1,"%"+valueToSearch+"%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            Object[] row;
            while(rs.next())
            {
                row=new Object[NUMBER_OF_ROWS_CLIENT];
                row[0] = rs.getInt(2);
                row[1] = rs.getString(3);
                row[2] = rs.getString(4);
                row[3] = rs.getString(5);
                row[4] = rs.getString(6);
                row[5] = rs.getString(7);
                row[6] = rs.getString(8);
                row[7] = rs.getString(9);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
