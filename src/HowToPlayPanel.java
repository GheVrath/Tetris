import javax.swing.*;
import java.awt.*;

public class HowToPlayPanel extends JPanel {
    public HowToPlayPanel(){
        int width=Settings.SIDE_PANEL_WIDTH;
        int height=(int)(Settings.SIDE_PANEL_HEIGHT*0.1);
        this.setSize(width,height);
        String instruction = new String("<html>" +
                "A,D - rotate<br>Arrows - move" +
                "</html>");
        JLabel label = new JLabel(instruction);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        this.add(label);
    }
}
