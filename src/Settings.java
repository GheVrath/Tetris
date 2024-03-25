/**
 * Here are settings of a game
 */
public final class Settings {
    public static int POSITIONS_AT_X=20;
    public static int POSITIONS_AT_Y=30;
    public static int CONTAINER_SIZE =34;
    public static int SET_FPS=30;
    public static int nanoSecPerFrame=1000000000/SET_FPS;
    public static int LEVEL=1;
    public static int GAME_PANEL_WIDTH=POSITIONS_AT_X*CONTAINER_SIZE;
    public static int GAME_PANEL_HEIGHT =POSITIONS_AT_Y*CONTAINER_SIZE;
    public static int SIDE_PANEL_HEIGHT=GAME_PANEL_HEIGHT;
    public static int SIDE_PANEL_WIDTH=300;
    public static int TEXT_SCALE=1;
    public static int MILLISECONDS_FOR_STEP=250;

}
