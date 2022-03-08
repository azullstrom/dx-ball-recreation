package sprites;

import java.awt.Color;
import java.awt.Graphics2D;

import interfaces.Movable;
import main.Constant;
import main.Key;
import main.Keyboard;
import states.GameState;

public class Bat extends Sprite implements Movable {
	private GameState gameState;
	private BatAnimation batAnimation;
	
	public Bat(int x, int y, int width, int height) {
		super(x, y, width, height);
		batAnimation = new BatAnimation(x, y, width, 2);
	}

	@Override
	public void update(Keyboard keyboard) {
		moveX(keyboard);
		batAnimation.update(keyboard);
		batAnimation.setGameState(gameState);
	}

	@Override
	public void draw(Graphics2D graphics) {
		drawBat(graphics);
		batAnimation.draw(graphics);
	}

	@Override
	public void moveX(Keyboard keyboard) {
		// LEFT/RIGHT
		if(keyboard.isKeyDown(Key.Left)) {
			setX(getX() - Constant.BAT_SPEED);
		}
		if(keyboard.isKeyDown(Key.Right)) {
			setX(getX() + Constant.BAT_SPEED);
		}	
		// HITTING WALLS
		if(getX() <= 5) {
			setX(5);
		}
		if(getX() >= Constant.WINDOW_WIDTH - Constant.BAT_WIDTH - 5) {
			setX(Constant.WINDOW_WIDTH - Constant.BAT_WIDTH - 5);
		}
	}

	@Override
	public void moveY(Keyboard keyboard) {
		
	}
	
	public void drawBat(Graphics2D graphics) {
		if(gameState == GameState.Idle) {
			graphics.setColor(Color.lightGray);
			graphics.fillOval(getX(), getY() - Constant.BAT_HEIGHT + 3, 2, getHeight());
			graphics.fillOval(getX() + Constant.BAT_WIDTH - 2, getY() - Constant.BAT_HEIGHT + 3, 2, getHeight());
		}
		graphics.setColor(Color.cyan);
		graphics.fillRoundRect(getX(), getY(), getWidth(), getHeight() + 3, getWidth(), getHeight());
		graphics.setColor(Color.white);
		graphics.fillRoundRect(getX(), getY(), getWidth(), getHeight() + 1, getWidth(), getHeight());
		graphics.setColor(Color.pink);
		graphics.fillOval(getX() + Constant.BAT_WIDTH - 5, getY(), 10, getHeight());
		graphics.fillOval(getX() - 5, getY(), 10, getHeight());
		graphics.setColor(Color.darkGray);
		graphics.fillRect(getX(), getY(), getWidth(), getHeight());	
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
}
