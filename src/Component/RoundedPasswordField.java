package Component;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPasswordField;


public class RoundedPasswordField extends JPasswordField{

    public RoundedPasswordField() {
        
         this.putClientProperty(FlatClientProperties.STYLE,"arc:20;");
        
    }
    
}
