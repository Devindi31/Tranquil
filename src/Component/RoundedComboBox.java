package Component;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JComboBox;

public class RoundedComboBox extends JComboBox<Object>{
    
    public RoundedComboBox(){
    
         this.putClientProperty(FlatClientProperties.STYLE,"arc:20;");
        
    }
    
}
