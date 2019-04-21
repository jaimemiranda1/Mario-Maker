package Game.GameStates;

import java.awt.Graphics;

import Display.UI.UIManager;
import Main.Handler;
import Resources.Images;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class LoadingState extends State {

    private UIManager uiManager;

    public LoadingState(Handler handler) {
        super(handler);
    }

    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.loading,0,0,handler.getWidth(),handler.getHeight(),null);
    }
}
