package Main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import Game.Entities.DynamicEntities.Luigi;
import Game.Entities.DynamicEntities.Mario;
import Game.World.Map;
import Input.Camera;
import Input.KeyManager;
import Input.MouseManager;


/**
 * Created by AlexVR on 7/1/2018.
 */

public class Handler {

    private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static final int DEFAULTWIDTH = gd.getDisplayMode().getWidth();
    public static final int DEFAULTHEIGHT = gd.getDisplayMode().getHeight();

    int width,height;

    private GameSetUp game;
    private Mario mario;
    private Luigi luigi;
    private Map map;
    private boolean marioInMap = false;
    private boolean luigiInMap = false;

    private Camera camera;
    private Camera cameraL;


    public Handler(){

        height=2*(DEFAULTHEIGHT/3)  ;
        width =height;

    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public GameSetUp getGame() {
        return game;
    }

    public void setGame(GameSetUp game) {
        this.game = game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }


    ///TO CHange
    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }
    
    public Luigi getLuigi() {
    	return luigi;
    }
    
    public void setLuigi(Luigi luigi) {
    	this.luigi = luigi;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isInMap() {
        return marioInMap;
    }
    public boolean isInMapL() {
        return luigiInMap;
    }

    public void setIsInMap(boolean is) {
        marioInMap = is;
    }
    public void setIsInMapL(boolean is) {
        luigiInMap = is;
    }

    public Camera getCamera() {
        return camera;
    }
    
    public Camera getCameraL() {
    	return cameraL;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    
    public void setCameraL(Camera cameraL) {
    	this.cameraL = cameraL;
    }
}
