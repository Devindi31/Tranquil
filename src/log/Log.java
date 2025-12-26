package log;

import java.util.logging.*;

public class Log {

    public static Logger log = null;
    private static FileHandler handler;

    static {
        try {
            log = Logger.getLogger("log1");
            handler = new FileHandler("log.txt", true);
            handler.setFormatter(new SimpleFormatter());
            log.addHandler(handler);

        } catch (Exception e) {
           //e.printStackTrace();
        }
    }
}
