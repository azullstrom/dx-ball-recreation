package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import interfaces.Movable;
import main.Constant;
import main.Key;
import main.Keyboard;
import states.BallState;
import states.BrickState;
import states.GameState;
import states.HitBatState;
import states.LifeState;

public class Ball extends Sprite implements Movable {
	private BrickState brickState;
	private GameState gameState;
	private BallState ballStateVertical, ballStateHorizontal;
	private HitBatState hitBatState;
	private LifeState lifeState;
	private PlayerLifeScreen playerLifeScreen;
	private int life;
	private int movementY, movementX;
	
	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);
		playerLifeScreen = new PlayerLifeScreen();
		ballStateVertical = BallState.Idle;
		hitBatState = HitBatState.Idle;
		movementY = Constant.BALL_SPEED;
		life = 3;
	}

	@Override
	public void update(Keyboard keyboard) {
		moveX(keyboard);
		moveY(keyboard);
		
		//Lose Life
		if(lifeState == LifeState.TakeLife) {
			playerLifeScreen.update(life);
			lifeState = LifeState.Idle;
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		playerLifeScreen.draw(graphics);
		
		graphics.setColor(Color.pink);
		graphics.fillOval(getX(), getY(), getWidth(), getHeight());
		graphics.setColor(Color.lightGray);
		graphics.fillOval(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4);
		graphics.setColor(Color.darkGray);
		graphics.fillOval(getX() + 4, getY() + 4, getWidth() - 8, getHeight() - 8);
		graphics.setColor(Color.gray);
		graphics.fillOval(getX() + 6, getY() + 6, getWidth() - 12, getHeight() - 12);
	}

	@Override
	public void moveX(Keyboard keyboard) {
		//Idle
		if(ballStateVertical == BallState.Idle) {
			if(keyboard.isKeyDown(Key.Left)) {
				setX(getX() - Constant.BAT_SPEED);
				if(getX() <= 45) {
					setX(45);
				}
			}
			if(keyboard.isKeyDown(Key.Right)) {
				setX(getX() + Constant.BAT_SPEED);
				if(getX() >= 740) {
					setX(740);
				}
			}
		}
		
		//Colliding with left/right wall or left/right bricks
		//RIGHT
		if(getX() <= 0) {
			ballStateHorizontal = BallState.GoingRight;
			hitBatState = HitBatState.Idle;
		} else if (brickState == BrickState.RightHit) {
			brickState = BrickState.NotHit;
			ballStateHorizontal = BallState.GoingRight;
			hitBatState = HitBatState.Idle;
		}
		if(ballStateHorizontal == BallState.GoingRight) {
			setX(getX() + movementX);
		}
		//LEFT
		if(getX() >= Constant.WINDOW_WIDTH - Constant.BALL_RADIUS) {
			ballStateHorizontal = BallState.GoingLeft;
			hitBatState = HitBatState.Idle;
		} else if (brickState == BrickState.LeftHit) {
			brickState = BrickState.NotHit;
			ballStateHorizontal = BallState.GoingLeft;
			hitBatState = HitBatState.Idle;
		}
		if(ballStateHorizontal == BallState.GoingLeft) {
			setX(getX() - movementX);
		}
		
		//Direction depending on where ball hit bat
		switch(hitBatState) {
			case HitEdgeLeft:
				movementY = Constant.BALL_SPEED - 5;
				movementX = Constant.BALL_SPEED;
				setX(getX() - movementX);
				break;
			case HitFarLeft:
				movementY = Constant.BALL_SPEED - 3;
				movementX = Constant.BALL_SPEED - 4;
				setX(getX() - movementX);
				break;
			case HitLeft:
				movementY = Constant.BALL_SPEED - 1;
				movementX = Constant.BALL_SPEED - 6;
				setX(getX() - movementX);
				break;
			case HitMid:
				movementY = Constant.BALL_SPEED;
				setX(getX());
				break;
			case HitRight:
				movementY = Constant.BALL_SPEED - 1;
				movementX = Constant.BALL_SPEED - 6;
				setX(getX() + movementX);
				break;
			case HitFarRight:
				movementY = Constant.BALL_SPEED - 3;
				movementX = Constant.BALL_SPEED - 4;
				setX(getX() + movementX);
				break;
			case HitEdgeRight:
				movementY = Constant.BALL_SPEED - 5;
				movementX = Constant.BALL_SPEED;
				setX(getX() + movementX);
				break;
			case Idle:
				break;
			default:
				break;		
		}
	}

	@Override
	public void moveY(Keyboard keyboard) {
		//Elevating/colliding with top bricks
		//UP
		if(gameState == GameState.Idle) {	
			if(keyboard.isKeyDown(Key.Space)) {
				ballStateVertical = BallState.Elevating;
			}
		}
		if(brickState == BrickState.TopHit) {
			brickState = BrickState.NotHit;
			ballStateVertical = BallState.Elevating;
		}
		if(ballStateVertical == BallState.Elevating) {
			setY(getY() - movementY);
		} 
		
		//Falling/colliding with roof or bot bricks
		//DOWN
		if(getY() <= 0) {
			ballStateVertical = BallState.Falling;
		} else if (brickState == BrickState.BotHit) {
			brickState = BrickState.NotHit;
			ballStateVertical = BallState.Falling;
		}
		if(ballStateVertical == BallState.Falling) {
			setY(getY() + movementY);
		}	
	}
	
	public boolean isCollidingBat(Bat bat) {
		Rectangle ballRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		Rectangle batEdgeLeft = new Rectangle(bat.getX(), bat.getY(), 3, bat.getHeight());
		Rectangle batFarLeft = new Rectangle(bat.getX() + 4, bat.getY(), 22, bat.getHeight());
		Rectangle batLeft = new Rectangle(bat.getX() + 27, bat.getY(), 16, bat.getHeight());
		Rectangle batMid = new Rectangle(bat.getX() + 44, bat.getY(), 13, bat.getHeight());
		Rectangle batRight = new Rectangle(bat.getX() + 58, bat.getY(), 16, bat.getHeight());
		Rectangle batFarRight = new Rectangle(bat.getX() + 75, bat.getY(), 22, bat.getHeight());
		Rectangle batEdgeRight = new Rectangle(bat.getX() + 98, bat.getY(), 3, bat.getHeight());
		
		if(ballRect.intersects(batEdgeLeft)) { //EdgeLeft
			ballStateHorizontal = BallState.Null;
			hitBatState = HitBatState.HitEdgeLeft;
			return ballRect.intersects(batEdgeLeft);
		} else if(ballRect.intersects(batFarLeft)) { //FarLeft
			ballStateHorizontal = BallState.Null;
			hitBatState = HitBatState.HitFarLeft;
			return ballRect.intersects(batFarLeft);
		} else if(ballRect.intersects(batLeft)) { //Left
			ballStateHorizontal = BallState.Null;
			hitBatState = HitBatState.HitLeft;
			return ballRect.intersects(batLeft);
		} else if(ballRect.intersects(batMid)) { //Mid
			ballStateHorizontal = BallState.Null;
			hitBatState = HitBatState.HitMid;
			return ballRect.intersects(batMid);
		} else if(ballRect.intersects(batRight)) { //Right
			ballStateHorizontal = BallState.Null;
			hitBatState = HitBatState.HitRight;
			return ballRect.intersects(batRight);
		} else if(ballRect.intersects(batFarRight)) { //FarRight
			ballStateHorizontal = BallState.Null;
			hitBatState = HitBatState.HitFarRight;
			return ballRect.intersects(batFarRight);
		} else if(ballRect.intersects(batEdgeRight)) { //EdgeRight
			ballStateHorizontal = BallState.Null;
			hitBatState = HitBatState.HitEdgeRight;
			return ballRect.intersects(batEdgeRight);
		} else {
			return false;
		}
	}
	
	//If ball falls under bottom screen edge
	public boolean outOfBounds(Bat bat) {
		if(getY() >= Constant.WINDOW_HEIGHT + Constant.BALL_RADIUS) {
			movementX = 0;
			movementY = Constant.BALL_SPEED;
			life -= 1;
			ballStateVertical = BallState.Idle;
			lifeState = LifeState.TakeLife;
			hitBatState = HitBatState.Idle;
			setX(bat.getX() + (Constant.BAT_WIDTH / 2) - (Constant.BALL_RADIUS / 2));
			setY(Constant.WINDOW_HEIGHT - Constant.BAT_HEIGHT - 25);
			setWidth(Constant.BALL_RADIUS);
			setHeight(Constant.BALL_RADIUS);
			return true;
		}
		return false;
	}
	
	public void setBallStateElevating() {
		ballStateVertical = BallState.Elevating;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public int getLife() {
		return life;
	}

	public LifeState getLifeState() {
		return lifeState;
	}
	
	public void setBrickState(BrickState brickState) {
		this.brickState = brickState;
	}
}
