
import java.sql.Date;
import javax.swing.JOptionPane;

/*
    this class handke the errors and responsible for the dialogs and creating logs
*/
public class handleError {
    
    /* 
        handle the dialog message
    
    */
    public static void showErrorMessage(boolean writeInLog, String message, String title)
    {
        int success;
        if (writeInLog == true)
            success = writeInLog(); 
        if (title==null)
        {
            title = "Error";
        } else
        {
            title = "Error: " + title;
        }
                    
        JOptionPane.showMessageDialog(null, message, title,JOptionPane.WARNING_MESSAGE);
    }
    
    /*
        write the event to the log
        return 1 if succeeded, 0 if failed.
    */
    static int writeInLog()
    {
       
        return 1;
    }
}
