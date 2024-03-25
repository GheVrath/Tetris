import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public Container[][] containers;
    private int boardSizeX;
    private int boardSizeY;
    private SidePanel sidePanel;

    /**
     * Creates a main game panel containing scene objects
     * @param boardSizeX width in pixels
     * @param boardSizeY height in pixels
     * @param sidePanel side panel object that is not part of block scene
     */
    public GamePanel(int boardSizeX,int boardSizeY,SidePanel sidePanel){
        this.sidePanel=sidePanel;
        containers=new Container[boardSizeX][boardSizeY];
        this.boardSizeX=boardSizeX;
        this.boardSizeY=boardSizeY;
        this.setSize(Settings.GAME_PANEL_WIDTH,Settings.GAME_PANEL_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setVisible(true);

    }

    /**
     * Creates containers that can hold elements of blocks
     */
    public void generateContainers(){
        for(int i=0;i<boardSizeX;i++) for(int j=0;j<boardSizeY;j++){
            Container tempContainer=new Container(i,j);
            tempContainer.add(new JLabel("x:"+i+" y:"+j));
            containers[i][j]=tempContainer;
            this.add(tempContainer);
        }
    }

    /**
     * Responsible for removing elements
     *Checks if any row is completed, if true it flashes white color and remove object
     * When elements in row are deleted upper ones fall down
     */
    public void checkRow(){
        int scale=1;
        for(int j=boardSizeY-1;j>=0;j--){
            boolean isRowFull=true;
            boolean nextRowIsFilled=true;
            for(int i=0;i<boardSizeX;i++){
                if(!containers[i][j].getIsFilled()){ isRowFull=false; break;}
                try{
                    if(!containers[i][j-1].getIsFilled()){nextRowIsFilled=false;}
                }catch(Exception e){System.out.println("Element out of bounds");}
            }
            if(isRowFull){
                if(nextRowIsFilled) scale++;
                for(int i=0;i<boardSizeX;i++){ //removed blocks becoming white
                    try{
                        Thread.sleep(10);
                        containers[i][j].setElementColor(Color.GRAY);
                        Thread.sleep(10);
                        containers[i][j].setElementColor(Color.WHITE);
                        Thread.sleep(10);
                        for(int k=0;k<scale;k++) sidePanel.increaseScore(scale);
                    }
                    catch(Exception e){}
                    finally {
                        containers[i][j].removeElement();
                        SwingUtilities.updateComponentTreeUI(this);}
                }
                for(int i=0;i<boardSizeX;i++){
                    for(int k=j;k-1>=0;k--){
                        if(containers[i][k-1].getIsFilled()){
                            Color tempColor=containers[i][k-1].getElementColor();
                            containers[i][k-1].removeElement();
                            containers[i][k].insertElement(tempColor);
                        }
                    }
                }
                SwingUtilities.updateComponentTreeUI(this);
                j++;
            }
        }
    }

    /**
     * Marking path of falling block on brighter color
     * @param cords color of marked path
     */
    public void lightUp(ShapeCords[] cords){
        for(int i=0;i<boardSizeX;i++) for(int j=0;j<boardSizeY;j++) containers[i][j].setOpaque(false);
        for(int j=0;j<cords.length;j++){
            int xIndex=cords[j].getX();
            for(int i=0;i<boardSizeY;i++){
                containers[xIndex][i].setOpaque(true);
            }
        }
        SwingUtilities.updateComponentTreeUI(this);
    }
}
