import javax.swing.*;
import java.awt.*;

/**
 * Class makes container that can contain elements of blocks
 *
 */
public final class Container extends JPanel {
    private int xPlace, yPlace;
    private boolean isFilled, isShapePart;
    private Element element;

    /**
     *Creates container that can hold separate element
     * of block (single square of block)
     * @param xPlace Pixel location on panel
     * @param yPlace Pixel location on panel
     */
    public Container(int xPlace,int yPlace){
        this.xPlace=xPlace;
        this.yPlace=yPlace;
        this.setLayout(null);
        this.setBackground(new Color(25,25,25));
        this.setOpaque(false);
        this.setSize(Settings.CONTAINER_SIZE, Settings.CONTAINER_SIZE);
        this.setLocation(xPlace* Settings.CONTAINER_SIZE,yPlace* Settings.CONTAINER_SIZE);
        this.setVisible(true);
        isFilled=false;
    }

    /**
     * Inserts single element of block into this container.
     * New element is creating in this methode.
     * @param color Color of element of block
     */
    public void insertElement(Color color){
        if(isFilled) return;
        this.element=new Element(color);
        isFilled=true;
        this.add(element);
    }

    /**
     * Removes element of block from this container
     */
    public void removeElement(){
        if(!isFilled) return;
        isFilled=false;
        isShapePart=false;
        this.remove(element);
    }

    /**
     * @return Boolean if this container is filled with element of block
     */
    public boolean getIsFilled(){
        return isFilled;
    }

    /**
     * @return Boolean if element in this container is part of moving block
     */
    public boolean isShapePart(){
        return isShapePart;
    }

    /**
     * @return Color of element of block in this container
     */
    public Color getElementColor(){
        return element.getColor();
    }

    /**
     * @param isIt Boolean to declare if element in this
     *             container is part of current moving block
     */
    public void isShapePart(Boolean isIt){
        isShapePart=isIt;
    }

    /**
     * @return Number that is both width and height of this container
     */
    public static int getContainerSize(){
        return Settings.CONTAINER_SIZE;
    }

    /**
     * Set color of element inside this container
     * @param color Color object
     */
    public void setElementColor(Color color){
        element.setBackground(color);
    }

    /**
     * This method is invoked to check if this object is fine
     */
    public void isReal(){
        //DO NOT REMOVE OR MODIFY THIS FUNCTION
    }
}
