import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

/**
 * Create a shape of a block
 */
public final class Shape implements KeyListener {
    private int Xmain;
    private int Ymain;
    private GamePanel gamePanel;
    private ShapeCords[] cords;
    private boolean isMovePossible;
    private boolean isFinished;
    private boolean isRotatable;
    private Color color;
    private SidePanel sidePanel;

    /**
     * Creates shape of a block
     * @param gamePanel Game panel with containers for elements of the blocks
     * @param sidePanel side panel with score board
     */
    public Shape(GamePanel gamePanel,SidePanel sidePanel){
        this.sidePanel=sidePanel;
        isMovePossible=false;
        isRotatable=true;
        isFinished =false;
        this.Xmain=Settings.POSITIONS_AT_X/2;
        this.Ymain=0;
        this.gamePanel=gamePanel;
        this.gamePanel.addKeyListener(this);
        this.gamePanel.requestFocus();
        makeShape();
        isMovePossible=true;
    }

    /**
     * Moves shape left
     */
    public void moveShapeLeft() {
        moveShapeSideways(-1);
    }

    /**
     * Moves shape right
     */
    public void moveShapeRight(){
        moveShapeSideways(1);
    }

    /**
     * Rotates shape counterclockwise
     */
    public void rotateShapeLeft(){
            if(checkRotateCollision(-1,1)){return;}
            rotateShape(-1,1);
    }

    /**
     * Rotates shape clockwise
     */
    public void rotateShapeRight(){
            if(checkRotateCollision(1,-1)){return;}
            rotateShape(1,-1);
    }

    /**
     * Moves shape down
     */
    public void moveShapeDown(){
        if(!isMovePossible){return;}
        isMovePossible=false;
        if(!isMoveDownPossible()){return;}
        for(int i=cords.length-1;i>=0;i--){
            int x=cords[i].getX();
            int y=cords[i].getY();
            gamePanel.containers[x][y].removeElement();
        }
        for(int i=cords.length-1;i>=0;i--){
            int x=cords[i].getX();
            int y=cords[i].getY();
            cords[i].setY(y+1);
            gamePanel.containers[x][y+1].insertElement(color);
            gamePanel.containers[x][y+1].isShapePart(true);
        }
        Ymain++;
        gamePanel.lightUp(cords);
        sidePanel.increaseScore(1.0f);
        isMovePossible=true;
    }

    /**
     * @return boolean if move down is possible
     */
    private boolean isMoveDownPossible(){
        for(int i=0;i<cords.length;i++){
            int x=cords[i].getX();
            int y=cords[i].getY();
            if(y+1>=Settings.POSITIONS_AT_Y){stopMovement();return false;}
            else if(gamePanel.containers[x][y+1].getIsFilled()&&!gamePanel.containers[x][y+1].isShapePart()){
                stopMovement();
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if move left or right is possible
     * if true it removes every element of the block
     * and creates new ones in demanded direction
     * @param direction Left:-1, Right:1
     */
    private void moveShapeSideways(int direction){
        if(!isMovePossible) return;
        isMovePossible=false;
        boolean canMove=true;
        int dirX=Math.abs(direction)/direction;
        for(int i=cords.length-1;i>=0;i--){
            int x=cords[i].getX();
            int y=cords[i].getY();
            if (x+1>=Settings.POSITIONS_AT_X && dirX>0||x-1<0 && dirX<0){canMove=false;break;}
            if (gamePanel.containers[x+dirX][y].getIsFilled()&&!gamePanel.containers[x+dirX][y].isShapePart()){
                canMove=false;
                break;
            }
        }
        if(canMove){
            for(int i=cords.length-1;i>=0;i--){
                int x=cords[i].getX();
                int y=cords[i].getY();
                gamePanel.containers[x][y].removeElement();
            }
            for(int i=cords.length-1;i>=0;i--){
                int x=cords[i].getX();
                int y=cords[i].getY();
                gamePanel.containers[x+dirX][y].insertElement(color);
                gamePanel.containers[x+dirX][y].isShapePart(true);
                cords[i].setX(x+dirX);
            }
            Xmain+=dirX;
            gamePanel.lightUp(cords);
        }
        isMovePossible=true;
    }

    /**
     * Stop movement of a block if no further move is possible
     * and block is no longer a shape
     */
    private void stopMovement(){
        isFinished =true;
        isMovePossible=false;
        for(int i=0;i<cords.length;i++){
            int x=cords[i].getX();
            int y=cords[i].getY();
            gamePanel.containers[x][y].isShapePart(false);
            cords[i].setX(0);
            cords[i].setY(0);
        }
    }

    /**
     * Sets a random color of a shape
     */
    private void setColor(){
        Random random=new Random();
        int r=random.nextInt(190)+65;
        int g=random.nextInt(190)+65;
        int b=random.nextInt(190)+65;
        color=new Color(r,g,b);
    }

    /**
     * When currently there is no possible move for shape
     * it creates a new random shape of a moving block
     * shape depends on a player level
     */
    private void makeShape() {
        isMovePossible=false;
        setColor();
        Random rand = new Random();
        int shape=0;
            switch(Settings.LEVEL){
                case 1,2,3->{shape=rand.nextInt(9)+1;}
                case 4->{shape=rand.nextInt(12)+1;}
                case 5->{shape=rand.nextInt(13)+1;}
                case 6->{shape=rand.nextInt(14)+1;}
                default -> {shape=rand.nextInt(14)+1;}
            }
        switch (shape) {
            case 1,2,3->{cords=new ShapeCords[4];makeLong(0);}
            case 4,5,6->{cords=new ShapeCords[4];makeSquare(0);}
            case 7,8,9->{cords=new ShapeCords[4];makeL(0);}
            case 10,11,12->{cords=new ShapeCords[4];makeT();}
            case 13->{cords=new ShapeCords[8];makeBall();}
            case 14->{cords=new ShapeCords[6];makeHorse();}
        }
            isMovePossible=true;
            gamePanel.lightUp(cords);
    }

    /**
     * Makes long shaped block
     * @param offsetY height offset
     */
    private void makeLong(int offsetY){
        Ymain=offsetY;
        Xmain-=2;
        for (int i=0;i<4;i++){
            gamePanel.containers[Xmain+i][Ymain].insertElement(color);
            gamePanel.containers[Xmain+i][Ymain].isShapePart(true);
            cords[i]=new ShapeCords(Xmain+i,Ymain);
        }
        Xmain+=1;
    }

    /**
     * Makes square shaped block
     * @param offsetY height offset
     */
    private void makeSquare(int offsetY){
        isRotatable=false;
        Ymain=offsetY;
        int iterate=-1;
        for(int i=0;i<2;i++) for(int j=0;j<2;j++){
            iterate++;
            gamePanel.containers[Xmain+i][j+Ymain].insertElement(color);
            gamePanel.containers[Xmain+i][j+Ymain].isShapePart(true);
            cords[iterate]=new ShapeCords(Xmain+i,Ymain+j);
        }
    }

    /**
     * Makes L-shaped block
     * @param offsetY height offset
     */
    private void makeL(int offsetY){
        Ymain=offsetY;
        for (int i=0;i<3;i++){
            gamePanel.containers[Xmain+i][Ymain].insertElement(color);
            gamePanel.containers[Xmain+i][Ymain].isShapePart(true);
            cords[i]=new ShapeCords(Xmain+i,Ymain);
        }
        gamePanel.containers[Xmain+2][Ymain+1].insertElement(color);
        gamePanel.containers[Xmain+2][Ymain+1].isShapePart(true);
        cords[3]=new ShapeCords(Xmain+2,Ymain+1);
        Xmain+=1;
    }

    /**
     * Makes T-shaped block
     */
    private void makeT(){
        Ymain=0;
        for (int i=0;i<3;i++){
            gamePanel.containers[Xmain+i][Ymain].insertElement(color);
            gamePanel.containers[Xmain+i][Ymain].isShapePart(true);
            cords[i]=new ShapeCords(Xmain+i,Ymain);
        }
        gamePanel.containers[Xmain+1][Ymain+1].insertElement(color);
        gamePanel.containers[Xmain+1][Ymain+1].isShapePart(true);
        cords[3]=new ShapeCords(Xmain+1,Ymain+1);
        Xmain+=1;
    }

    /**
     * Makes horse shaped block
     */
    private void makeHorse(){
        makeL(1);
        Xmain-=1;
        gamePanel.containers[Xmain][Ymain+1].insertElement(color);
        gamePanel.containers[Xmain][Ymain+1].isShapePart(true);
        cords[4]=new ShapeCords(Xmain,Ymain+1);
        gamePanel.containers[Xmain+2][Ymain-1].insertElement(color);
        gamePanel.containers[Xmain+2][Ymain-1].isShapePart(true);
        cords[5]=new ShapeCords(Xmain+2,Ymain-1);
        Xmain+=1;
    }

    /**
     * Makes ball-shaped block
     */
    private void makeBall(){
        isRotatable=false;
        makeL(0);
        Xmain-=1;
        for(int i=0;i<3;i++){
            gamePanel.containers[Xmain+i][Ymain+2].insertElement(color);
            gamePanel.containers[Xmain+i][Ymain+2].isShapePart(true);
            cords[4+i]=new ShapeCords(Xmain+i,Ymain+2);
        }
        gamePanel.containers[Xmain][Ymain+1].insertElement(color);
        gamePanel.containers[Xmain][Ymain+1].isShapePart(true);
        cords[7]=new ShapeCords(Xmain,Ymain+1);
        Xmain+=1;
        Ymain+=1;
    }

    /**
     * @param directionX rotation direction -1 or 1
     * @param directionY rotation direction -1 or 1
     * @return boolean if shape can rotate without collision
     */
    private boolean checkRotateCollision(int directionX,int directionY){
        if(!isMovePossible) return true;
        if(!isRotatable) return true;
        isMovePossible=false;
        int dirX=Math.abs(directionX)/directionX;
        int dirY=Math.abs(directionY)/directionY;
        boolean isColliding=false;
        for(int i=0;i<cords.length;i++){
            int x=cords[i].getX();
            int y=cords[i].getY();
            int diffX=Math.abs(Xmain-x);
            int diffY=Math.abs(Ymain-y);
            try{
                gamePanel.containers[Xmain + diffY*dirY][Ymain + diffX*dirX].isReal();
                gamePanel.containers[Xmain - diffY*dirY][Ymain - diffX*dirX].isReal();
            }catch(Exception e ){isColliding=true;isMovePossible=true; return true;}
            boolean cond1=gamePanel.containers[Xmain + diffY*dirY][Ymain + diffX*dirX].getIsFilled();
            boolean cond2=gamePanel.containers[Xmain + diffY*dirY][Ymain + diffX*dirX].isShapePart();
            if(cond1&&!cond2){isColliding=true; break;}
        }
        isMovePossible=true;
        return isColliding;
    }

    /**
     * If not collides with other blocks, shape is rotated
     * @param directionX rotation direction -1 or 1
     * @param directionY rotation direction -1 or 1
     */
    private void rotateShape(int directionX, int directionY){
        if(!isMovePossible) return;
        isMovePossible=false;
        int dirX=Math.abs(directionX)/directionX;
        int dirY=Math.abs(directionY)/directionY;
        for(int i=0;i<cords.length;i++){
            int x=cords[i].getX();
            int y=cords[i].getY();
            gamePanel.containers[x][y].removeElement();
        }
        for(int i=0;i<cords.length;i++){
            int x=cords[i].getX();
            int y=cords[i].getY();
            int diffX=Xmain-x;
            int diffY=Ymain-y;
            cords[i].setX(Xmain + diffY*dirX);
            cords[i].setY(Ymain + diffX*dirY);
        }
        for(int i=0;i<cords.length;i++){
            int x=cords[i].getX();
            int y=cords[i].getY();
            gamePanel.containers[x][y].insertElement(color);
            gamePanel.containers[x][y].isShapePart(true);
        }
        gamePanel.lightUp(cords);
        isMovePossible=true;
    }

    /**
     * @return boolean if move is possible
     */
    public boolean isMovePossible(){
        return isMovePossible;
    }

    /**
     * @return Color af elements in shape
     */
    public Color getColor(){return color;}
    public boolean isFinished(){return isFinished;}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Steering wih keyboard
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==VK_LEFT) moveShapeLeft();
        else if(e.getKeyCode()==VK_RIGHT) moveShapeRight();
        else if(e.getKeyCode()==VK_DOWN) moveShapeDown();
        else if(e.getKeyChar()=='a') rotateShapeLeft();
        else if(e.getKeyChar()=='d') rotateShapeRight();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
