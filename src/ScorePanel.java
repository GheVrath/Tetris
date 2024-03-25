import javax.swing.*;
import java.awt.*;

/**
 * Score panel
 * points calculations
 */
public class ScorePanel extends JPanel implements Runnable{
    private JPanel innerScorePanel;
    private Thread thread;
    private int scoreNumber=-1;
    private float colorHue=0;
    private float colorSaturation=0;
    private float colorDirection=0.01f;
    private float scoreScale=1.0f;
    private int locationY;
    private int locationX;
    private JLabel scoreLabel;

    /**
     * Creates an information panel about score
     * @param locationY Pixel place on MainContainer object
     */
    public ScorePanel(int locationY){
        this.locationY=locationY;
        int width=(int)(Settings.SIDE_PANEL_WIDTH*0.8);
        int height=(int)(Settings.SIDE_PANEL_HEIGHT*0.1);
        this.setSize(width,height);
        locationX=(Settings.SIDE_PANEL_WIDTH-width)/2;
        this.setLocation(locationX,locationY);
        this.setLayout(null);
        this.setOpaque(true);
        this.setVisible(true);

        innerScorePanel =new JPanel();
        innerScorePanel.setBackground(Color.LIGHT_GRAY);
        innerScorePanel.setOpaque(false);
        int scoreHeight=(int)(height*0.66);
        int scoreLocationY=height-scoreHeight;
        innerScorePanel.setBounds(0,scoreLocationY,width,scoreHeight);
        innerScorePanel.setLayout(null);
        innerScorePanel.setVisible(true);

        scoreLabel=new JLabel();
        scoreLabel.setText(""+scoreNumber);
        scoreLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,scoreHeight));
        scoreLabel.setBounds(10,0,width,scoreHeight);
        scoreLabel.setOpaque(false);
        scoreLabel.setVisible(true);


        JPanel name=new JPanel();
        name.setBounds(0,0,width,scoreLocationY);
        name.setVisible(true);
        name.setLayout(null);
        name.setOpaque(false);

        JLabel label=new JLabel();
        label.setText("  SCORE");
        int labelWidth=label.getText().length()*scoreLocationY;
        int labelLocationX=(width-labelWidth)/2;
        label.setFont(new Font(Font.SANS_SERIF,Font.BOLD,scoreLocationY));
        label.setBounds(labelLocationX,0,width,scoreLocationY);

        innerScorePanel.add(scoreLabel);
        this.add(innerScorePanel);
        name.add(label);
        this.add(name);

        increaseScore(5);
    }

    /**
     * Run a new thread responsible for increasing player points
     * and set multiplier of gaining points
     * @param scoreScale Multiplier of gained points
     */
    public final void increaseScore(float scoreScale){
        if(scoreScale<=0) this.scoreScale=1.0f;
        else this.scoreScale=scoreScale;
        thread=new Thread(this);
        thread.start();
    }

    /**
     * Visual effect of shaking score board
     * the higher multiplier of gaining points the more
     * visible it appears
     */
    private void woahEffect(){
        scoreNumber+=scoreScale;
        scoreLabel.setText(""+scoreNumber);
        if(colorHue>=1.0f) colorDirection*=(-1.0f);
        colorHue+=colorDirection*this.scoreScale;
        if(scoreNumber%100==0&&colorSaturation<1.0f) {colorSaturation+=Math.abs(0.01f*this.scoreScale);if(colorSaturation>1.0f) colorSaturation=1.0f;}
        this.setBackground(Color.getHSBColor(colorHue,colorSaturation,1.0f));
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * @return Player score
     */
    public final int getScore(){return scoreNumber;}

    /**
     * Loop responsible for animating score board
     */
    @Override
    public void run() {
        try{
            int sizeX=this.getSize().width;
            int sizeY=this.getSize().height;
            int multiplyX=0;
            int multiplyY=0;
            switch(Settings.LEVEL){
                case 1,2,3->{multiplyX=0;multiplyY=0;}
                case 4->{multiplyX=2;multiplyY=1;}
                case 5->{multiplyX=4;multiplyY=2;}
                case 6->{multiplyX=6;multiplyY=3;}
                case 7->{multiplyX=8;multiplyY=4;}
            }
            if(scoreNumber<=1500){this.setBounds(locationX-multiplyX,locationY-multiplyY,sizeX,sizeY);}
            else this.setBounds(locationX-multiplyX,locationY-multiplyY,sizeX+multiplyX*2,sizeY+multiplyY*2);
            woahEffect();
            Thread.sleep(30);

            int x=(int)(Settings.SIDE_PANEL_WIDTH*0.8);
            int y=(int)(Settings.SIDE_PANEL_HEIGHT*0.1);
            this.setBounds(locationX,locationY,x,y);
            SwingUtilities.updateComponentTreeUI(this);

        }catch(Exception e){}
    }
}
