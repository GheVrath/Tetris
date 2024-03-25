import javax.swing.*;

/**
 * Creates a window of application
 */
public class GameWindow extends JFrame {

    /**
     * Creates a window of application and adding main panel into it
     */
    public GameWindow(MainContainer mainContainer){
        this.add(mainContainer);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
    }
}
