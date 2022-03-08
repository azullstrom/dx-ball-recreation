package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Constant;
import main.Keyboard;
import states.BrickState;
import states.GameState;

public class BrickMap {
	private BrickState brickState;
	private GameState gameState;
	private ArrayList<Brick> bricks;
	private int score;
	
	public BrickMap() {
		bricks = new ArrayList<Brick>();
		
		drawBrickOneHits(Color.white, Color.lightGray);
		drawBrickTwoHits(Color.yellow, Color.gray);
		drawBrickThreeHits(Color.orange, Color.darkGray);
		//drawInvisible(Color.black, Color.pink);
		drawExtra(Color.green, Color.blue);
	}

	public void update(Keyboard keyboard, Ball ball) {
		if(bricks.isEmpty()) {
			setGameState(GameState.Ending);
		}
		
		for(Brick brick : bricks) {
			brick.update(keyboard);
			if(brick.isCollidingBall(ball)) {
				brickState = brick.getBrickState();
				
				if(brick.getClass() == Brick.class) {
					score += 10;
					bricks.remove(brick);
					return;
				}
				
				if(brick.getClass() == BrickTwoHit.class) {
					score += 10;
					brick.setEdgeColor(Color.white);
					brick.setMidColor(Color.lightGray);
					brick.setHitCount();
					if(brick.getHitCount() == 2) {
						bricks.remove(brick);
						return;
					}
				}
				
				if(brick.getClass() == BrickThreeHit.class) {
					score += 10;
					brick.setHitCount();
					if(brick.getHitCount() == 1) {
						brick.setEdgeColor(Color.yellow);
						brick.setMidColor(Color.gray);
					}
					if(brick.getHitCount() == 2) {
						brick.setEdgeColor(Color.white);
						brick.setMidColor(Color.lightGray);
					}
					if(brick.getHitCount() == 3) {
						bricks.remove(brick);
						return;
					}
				}
				
				if(brick.getClass() == BrickInvisible.class) {
					score += 30;
					brick.setHitCount();
					if(brick.getHitCount() == 2) {
						bricks.remove(brick);
						return;
					}
					
				}
				
				if(brick.getClass() == BrickExtraPoint.class) {
					score += 100;
					bricks.remove(brick);
					return;
				}
			}
		}
	}

	public void draw(Graphics2D graphics) {
		for(Brick brick : bricks) {
			if(bricks.contains(brick))
				brick.draw(graphics);
		}
	}
	
	public void drawBrickOneHits(Color edgeColor, Color midColor) {
		for(int i = 0; i < 14; i++) {
			{
				int x = 50 + i % 14 * (Constant.BRICK_WIDTH);
				int y = 100 + i / 14 * (Constant.BRICK_HEIGHT);
				bricks.add(new Brick(x, y, Constant.BRICK_WIDTH, Constant.BRICK_HEIGHT, edgeColor, midColor));
			}
			{
				int x = 50 + i % 14 * (Constant.BRICK_WIDTH);
				int y = 100 + (Constant.BRICK_HEIGHT * 5) + i / 14 * (Constant.BRICK_HEIGHT);
				bricks.add(new Brick(x, y, Constant.BRICK_WIDTH, Constant.BRICK_HEIGHT, edgeColor, midColor));
			}
		}
	}
	
	public void drawBrickTwoHits(Color edgeColor, Color midColor) {
		// LEFT
		for(int i = 0; i < 5; i++) {
			{
				int x = Constant.BRICK_WIDTH + 50 + i % 5 * (Constant.BRICK_WIDTH);
				int y = 100 + Constant.BRICK_HEIGHT + i / 5 * (Constant.BRICK_HEIGHT);
				bricks.add(new BrickTwoHit(x, y, Constant.BRICK_WIDTH, Constant.BRICK_HEIGHT, edgeColor, midColor));
			}
			{
				int x = Constant.BRICK_WIDTH + 50 + i % 5 * (Constant.BRICK_WIDTH);
				int y = 100 + (Constant.BRICK_HEIGHT * 4) + i / 5 * (Constant.BRICK_HEIGHT);
				bricks.add(new BrickTwoHit(x, y, Constant.BRICK_WIDTH, Constant.BRICK_HEIGHT, edgeColor, midColor));
			}
		// RIGHT
			{
				int x = (Constant.BRICK_WIDTH * 8) + 50 + i % 5 * (Constant.BRICK_WIDTH);
				int y = 100 + Constant.BRICK_HEIGHT + i / 5 * (Constant.BRICK_HEIGHT);
				bricks.add(new BrickTwoHit(x, y, Constant.BRICK_WIDTH, Constant.BRICK_HEIGHT, edgeColor, midColor));
			}
			{
				int x = (Constant.BRICK_WIDTH * 8) + 50 + i % 5 * (Constant.BRICK_WIDTH);
				int y = 100 + (Constant.BRICK_HEIGHT * 4) + i / 5 * (Constant.BRICK_HEIGHT);
				bricks.add(new BrickTwoHit(x, y, Constant.BRICK_WIDTH, Constant.BRICK_HEIGHT, edgeColor, midColor));
			}
		}
	}
	
	public void drawBrickThreeHits(Color edgeColor, Color midColor) {
		// LEFT
		for(int i = 0; i < 6; i++) {
			{
				int x = (Constant.BRICK_WIDTH * 2) + 50 + i % 3 * (Constant.BRICK_WIDTH);
				int y = 100 + Constant.BRICK_HEIGHT * 2 + i / 3 * (Constant.BRICK_HEIGHT);
				bricks.add(new BrickThreeHit(x, y, Constant.BRICK_WIDTH, Constant.BRICK_HEIGHT, edgeColor, midColor));
			}
		// RIGHT
			{
				int x = (Constant.BRICK_WIDTH * 9) + 50 + i % 3 * (Constant.BRICK_WIDTH);
				int y = 100 + Constant.BRICK_HEIGHT * 2 + i / 3 * (Constant.BRICK_HEIGHT);
				bricks.add(new BrickThreeHit(x, y, Constant.BRICK_WIDTH, Constant.BRICK_HEIGHT, edgeColor, midColor));
			}
		}
	}
	
	public void drawInvisible(Color edgeColor, Color midColor) {
		//Inverted bricks
		//Bot
		bricks.add(new BrickInvisible(
			Constant.WINDOW_WIDTH / 2 - Constant.BRICK_WIDTH / 2, 
			175 + Constant.BRICK_HEIGHT, 
			Constant.BRICK_WIDTH, 
			Constant.BRICK_HEIGHT,
			edgeColor,
			midColor
		));
		//Top
		bricks.add(new BrickInvisible(
			Constant.WINDOW_WIDTH / 2 - Constant.BRICK_WIDTH / 2, 
			175 - Constant.BRICK_HEIGHT, 
			Constant.BRICK_WIDTH, 
			Constant.BRICK_HEIGHT,
			edgeColor,
			midColor
		));
		//Left
		bricks.add(new BrickInvisible(
			Constant.WINDOW_WIDTH / 2 - Constant.BRICK_WIDTH / 2 - Constant.BRICK_WIDTH, 
			175, 
			Constant.BRICK_WIDTH, 
			Constant.BRICK_HEIGHT,
			edgeColor,
			midColor
		));
		//Right
		bricks.add(new BrickInvisible(
			Constant.WINDOW_WIDTH / 2 - Constant.BRICK_WIDTH / 2 + Constant.BRICK_WIDTH, 
			175, 
			Constant.BRICK_WIDTH, 
			Constant.BRICK_HEIGHT,
			edgeColor,
			midColor
		));
	}
	
	public void drawExtra(Color edgeColor, Color midColor) {
		//ARTISTIC/EXTRA OBJECTS
		//Extra points
		bricks.add(new BrickExtraPoint(
			Constant.WINDOW_WIDTH / 2 - Constant.BRICK_WIDTH / 2, 
			175, 
			Constant.BRICK_WIDTH, 
			Constant.BRICK_HEIGHT,
			edgeColor,
			midColor
		));
	}
	
	private void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public BrickState getBrickState() {
		return brickState;
	}

	public void setBrickState(BrickState brickState) {
		this.brickState = brickState;
	}
	
	public int getScore() {
		return this.score;
	}

}
