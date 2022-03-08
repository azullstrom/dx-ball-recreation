package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import main.Constant;
import main.Keyboard;
import states.BrickState;

public class Brick extends Sprite {
	private BrickState brickState;
	private Color edgeColor, midColor;
	private Rectangle brickRectBot, brickRectTop, brickRectLeft, brickRectRight;
	private int hitCount;

	public Brick(int x, int y, int width, int height, Color edgeColor, Color midColor) {
		super(x, y, width, height);
		this.edgeColor = edgeColor;
		this.midColor = midColor;
		brickState = BrickState.NotHit;
	}

	@Override
	public void update(Keyboard keyboard) {
		
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.gray);
		graphics.fillRect(getX(), getY(), getWidth(), getHeight());
		graphics.setColor(Color.orange);
		graphics.drawRect(getX() + 1, getY() + 1, getWidth() - 2, getHeight() - 2);
		graphics.setColor(Color.black);
		graphics.drawRect(getX(), getY(), getWidth(), getHeight());
		graphics.setColor(edgeColor);
		graphics.fillRect(getX() + 5, getY() + 5, getWidth() - 10, getHeight() - 10);
		graphics.setColor(midColor);
		graphics.fillRect(getX() + 10, getY() + 10, getWidth() - 20, getHeight() - 20);	
	}
	
	public boolean isCollidingBall(Ball ball) {
		Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

		brickRectBot = new Rectangle(getX(), getY() + Constant.BRICK_HEIGHT, getWidth(), 1);
		brickRectTop = new Rectangle(getX(), getY(), getWidth(), 1);
		brickRectLeft = new Rectangle(getX(), getY(), 1, getHeight());
		brickRectRight = new Rectangle(getX() + Constant.BRICK_WIDTH, getY(), 1, getHeight());
		
		// If ball intersects brick
		if(brickRectBot.intersects(ballRect)) {
			brickState = BrickState.BotHit;
			int y = ball.getY();
			ball.setY(y + 10);
			return true;
		}
		if(brickRectTop.intersects(ballRect)) {
			brickState = BrickState.TopHit;
			int y = ball.getY();
			ball.setY(y - 10);
			return true;
		}
		if(brickRectLeft.intersects(ballRect)) {
			brickState = BrickState.LeftHit;
			int x = ball.getX();
			ball.setX(x - 10);
			return true;
		}
		if(brickRectRight.intersects(ballRect)) {
			brickState = BrickState.RightHit;
			int x = ball.getX();
			ball.setX(x + 10);
			return true;
		}
		else {
			return false;
		}
	}

	public BrickState getBrickState() {
		return brickState;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}

	public void setMidColor(Color midColor) {
		this.midColor = midColor;	
	}
	
	public Color getMidColor() {
		return this.midColor;
	}

	public void setHitCount() {
		hitCount++;
	}

	public int getHitCount() {
		return hitCount;
	}
}
