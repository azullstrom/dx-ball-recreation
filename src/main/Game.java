package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.*;

import javax.swing.JList;

import sprites.Ball;
import sprites.Bat;
import sprites.Brick;
import sprites.BrickMap;
import sprites.PlayerLifeScreen;
import sprites.ScoreScreen;
import states.BrickState;
import states.GameState;
import states.LifeState;

public class Game {
	private GameState gameState;
	private Bat bat;
	private BrickMap brickMap;
	private Ball ball;
	private ScoreScreen scoreScreen;
	private Font font;
	private int tickCount, tickCountTwo;
	private boolean gamePaused;

	public Game(GameBoard board) {
		gameState = GameState.Idle;
		
		bat = new Bat((
			Constant.WINDOW_WIDTH / 2) - (Constant.BAT_WIDTH / 2), 
			Constant.WINDOW_HEIGHT - 25,
			Constant.BAT_WIDTH, 
			Constant.BAT_HEIGHT);

		brickMap = new BrickMap();

		ball = new Ball((
			Constant.WINDOW_WIDTH / 2) - (Constant.BALL_RADIUS / 2),
			Constant.WINDOW_HEIGHT - Constant.BAT_HEIGHT - 25, 
			Constant.BALL_RADIUS, 
			Constant.BALL_RADIUS);
		
		scoreScreen = new ScoreScreen(board);
		
		font = new Font("Verdana", Font.BOLD, 50);
		
		gamePaused = false;
	}

	public void update(Keyboard keyboard) {
		// Game states
		ball.setGameState(gameState);
		
		if(keyboard.isKeyDown(Key.Space))
			gameState = GameState.Running;
		
		if(keyboard.isKeyDown(Key.Escape)) {
			tickCount ++;
			if(tickCount == 1)
				gamePaused = !gamePaused;
		}
		if(keyboard.isKeyUp(Key.Escape))
			tickCount = 0;
		
		if(ball.outOfBounds(bat))
			gameState = GameState.Idle;
		
		if(ball.getLife() == 0) 
			gameState = GameState.Ending;
		
		// Update
		if(gamePaused == false) {
			// Bat
			bat.update(keyboard);
			bat.setGameState(gameState);
			
			// BrickMap
			brickMap.update(keyboard, ball);
			checkBrickState();	
			if(brickMap.getGameState() == GameState.Ending)
				gameState = GameState.Ending;

			// Ball
			ball.update(keyboard);
			if(ball.isCollidingBat(bat)) {
				ball.setBallStateElevating();
			}
			
			//ScoreScreen
			scoreScreen.update(keyboard);
		} 
	
		tickCountTwo++;
	}

	public void draw(Graphics2D graphics) {
		if(gameState != GameState.Ending) {
			bat.draw(graphics);
			if(gamePaused == false) {
				brickMap.draw(graphics);
				ball.draw(graphics);
			} else if(gamePaused) {
				graphics.setFont(font);
				graphics.setColor(Color.pink);
				if(tickCountTwo % 40 >= 20) 
					graphics.drawString("GAME PAUSED", Constant.WINDOW_WIDTH / 2 / 2, Constant.WINDOW_HEIGHT / 2);
			}
		}
		scoreScreen.draw(graphics, brickMap.getScore(), gameState);
	}
	
	public void checkBrickState() {
		if(brickMap.getBrickState() == BrickState.BotHit) {
			ball.setBrickState(brickMap.getBrickState());
			brickMap.setBrickState(BrickState.NotHit);
		} else if(brickMap.getBrickState() == BrickState.TopHit) {
			ball.setBrickState(brickMap.getBrickState());
			brickMap.setBrickState(BrickState.NotHit);
		} else if(brickMap.getBrickState() == BrickState.LeftHit) {
			ball.setBrickState(brickMap.getBrickState());
			brickMap.setBrickState(BrickState.NotHit);
		} else if(brickMap.getBrickState() == BrickState.RightHit) {
			ball.setBrickState(brickMap.getBrickState());
			brickMap.setBrickState(BrickState.NotHit);
		}
	}
}
