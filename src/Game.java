/**\
 * Class responsible game loop and rendering
 *
 */
public class Game implements Runnable {
    private Shape shape;
    private GamePanel gamePanel;
    private SidePanel sidePanel;
    private Thread gameLoopThread;
    private MainContainer mainContainer;
    private static long start;

    /**
     * Creates main frame and panels inside
     */
    public Game(){
        sidePanel=new SidePanel();
        gamePanel=new GamePanel(Settings.POSITIONS_AT_X,Settings.POSITIONS_AT_Y,sidePanel);
        mainContainer=new MainContainer(gamePanel,sidePanel);
        new GameWindow(mainContainer);
        prepareGameLoop();
    }

    /**
     * Makes containers that can hold blocks
     * and start thread
     */
    private void prepareGameLoop(){
        gamePanel.generateContainers();
        shape = new Shape(gamePanel,sidePanel);
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    /**
     * Checks if block has move option, if false creates new shape
     * @param shape Current moving block
     */
    private void shapeManagement(Shape shape){
        if(shape.isFinished()){
            gamePanel.checkRow();
            this.shape=new Shape(gamePanel,sidePanel);
        }
    }

    /**
     * Resets time of next while loop run
     */
    public static void resetTimeRender(){
        start=System.currentTimeMillis();
    }

    /**
     * Game loop run method
     */
    @Override
    public void run() {
        start=System.currentTimeMillis();
        long lastIteration = 0;
        long currentIteration = System.currentTimeMillis();
        while(true){
            currentIteration = System.currentTimeMillis();
            if(currentIteration - lastIteration >= 10){
                lastIteration = System.currentTimeMillis();
                if(System.currentTimeMillis()-start>=Settings.MILLISECONDS_FOR_STEP){
                    shape.moveShapeDown();
                    shapeManagement(shape);
                    resetTimeRender();
                }
            }
        }
    }
}
