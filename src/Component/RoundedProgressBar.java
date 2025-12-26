package Component;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JProgressBar;

public class RoundedProgressBar extends JProgressBar{
    
        public RoundedProgressBar(){
    
        init();
        
    }
    
    private void init(){
    
        this.putClientProperty(FlatClientProperties.STYLE,"arc:100;");
        
    }
    
}
