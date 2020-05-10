import Logic.Controller;
import UI.Window;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Controller c = new Controller();
        Window w = new Window();
        w.setController(c);
        c.setWindow(w);
    }
}
