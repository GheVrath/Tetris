import javax.swing.*;
import java.awt.*;

/**
 * Element (a single square) that is part of a block
 */
public class Element extends JPanel {
    private Color color;

    /**
     * Constructs element which is part of block
     * @param color Color type object
     */
    public Element(Color color){
        setColor(color);
        int elementSize=(int)(Container.getContainerSize()*0.8);
        int border=(Container.getContainerSize()-elementSize)/2;
        this.setBounds(border,border,elementSize,elementSize);
        this.setVisible(true);
    }

    /**
     * @return Color of this element
     */
    public Color getColor(){
        return color;
    }

    /**
     * Sets color of this element
     * @param color of this element
     */
    public void setColor(Color color){
        this.color=color;
        this.setBackground(color);
    }
}
