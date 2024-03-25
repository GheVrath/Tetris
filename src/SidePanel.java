import javax.swing.*;
import java.awt.*;

/**
 * Panel for game info
 */
public class SidePanel extends JPanel{
    ScorePanel scorePanel;

    /**
     * Creates a side panel with information about game
     */
    public SidePanel(){
        scorePanel=new ScorePanel(50);
        this.add(scorePanel);
        this.setSize(Settings.SIDE_PANEL_WIDTH,Settings.GAME_PANEL_HEIGHT);
        this.setBackground(Color.WHITE);

        HowToPlayPanel howToPlayPanel = new HowToPlayPanel();
        howToPlayPanel.setLocation(0,getHeight() - howToPlayPanel.getHeight());

        this.add(howToPlayPanel);
        this.setLayout(null);
        this.setVisible(true);
    }

    /**
     * Increase player level depending on score
     * the more points player have the faster blocks are falling
     * @param scale Multiplier of point player gaining
     */
    public void increaseScore(float scale){
        scorePanel.increaseScore(scale);
        switch(scorePanel.getScore()/100){
            case 0,1->{Settings.LEVEL=1;}
            case 2,3->{Settings.LEVEL=2;}
            case 4->{Settings.LEVEL=3;}
            case 5->{Settings.LEVEL=4;}
            case 15->{Settings.LEVEL=5;}
            case 25->{Settings.LEVEL=6;}
            case 40->{Settings.LEVEL=7;}
        }
        if(scorePanel.getScore()%1000==0 && Settings.MILLISECONDS_FOR_STEP>=200){
            Settings.MILLISECONDS_FOR_STEP-=10;
        }
    }
}
