package Component;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JTextField;


public class RoundedTextField extends JTextField{

    public RoundedTextField() {
        
         this.putClientProperty(FlatClientProperties.STYLE,"arc:15;");
        
    }
    
}
