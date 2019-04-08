package Game.GameStates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Display.UI.UIManager;
import Display.UI.UIStringButton;
import Main.Handler;
import Resources.Images;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

	private UIManager uiManager;

	public GameOverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		uiManager.addObjects(new UIStringButton(56, 100, 128, 64, "Retry", () -> {
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			State.setState(handler.getGame().gameState);
		},handler,Color.YELLOW));

		uiManager.addObjects(new UIStringButton(56, 100+(64), 128, 64, "Options", () -> {
			handler.getMouseManager().setUimanager(null);
			handler.setIsInMap(false);
			State.setState(handler.getGame().menuState);
		},handler,Color.YELLOW));

		uiManager.addObjects(new UIStringButton(56, 100+(128), 128, 64, "Title", () -> {
			handler.getMouseManager().setUimanager(null);
			handler.setIsInMap(false);
			State.setState(handler.getGame().menuState);
		},handler,Color.YELLOW));

	}

	@Override
	public void tick() {
		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.gameOver,0,0,handler.getWidth(),handler.getHeight(),null);
		uiManager.Render(g);
	}

}
