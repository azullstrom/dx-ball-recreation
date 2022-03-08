package sprites;

import java.awt.Color;
import java.awt.Graphics2D;

import interfaces.Movable;
import main.Constant;
import main.Key;
import main.Keyboard;
import states.GameState;

public class BatAnimation extends Sprite {
	private GameState gameState;
	
	public BatAnimation(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void update(Keyboard keyboard) {
		setY(getY() + 2);
		if(getY() >= 590) {
			setY(575);
		}
		
		if(keyboard.isKeyDown(Key.Left)) {
			setX(getX() - Constant.BAT_SPEED);
		}
		if(keyboard.isKeyDown(Key.Right)) {
			setX(getX() + Constant.BAT_SPEED);
		}
		if(getX() <= 5) {
			setX(5);
		}
		if(getX() >= Constant.WINDOW_WIDTH - Constant.BAT_WIDTH - 5) {
			setX(Constant.WINDOW_WIDTH - Constant.BAT_WIDTH - 5);
		}
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.gray);
		graphics.fillOval(getX(), getY(), getWidth(), getHeight());
		if(gameState == GameState.Idle) {
			graphics.setColor(Color.cyan);
			graphics.fillOval(getX(), getY() - Constant.BAT_HEIGHT, getWidth(), getHeight());
		}
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
}
