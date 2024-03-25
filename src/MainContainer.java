import javax.swing.*;
import java.awt.*;

/**
 * Main panel that holds game scene and side panel
 */
public class MainContainer extends JPanel {
    /**
     * Main panel that holds game scene and side panel
     * @param gamePanel Main game panel with blocks
     * @param sidePanel Side panel with info about game
     */
    public MainContainer(GamePanel gamePanel,SidePanel sidePanel){

        int width=gamePanel.getWidth()+sidePanel.getWidth();
        int height=Settings.GAME_PANEL_HEIGHT;
        gamePanel.setLocation(0,0);
        sidePanel.setLocation(gamePanel.getWidth(),0);

        this.setPreferredSize(new Dimension(width,height));
        this.add(gamePanel);
        this.add(sidePanel);
        this.setLayout(null);
        this.setVisible(true);
    }
}
