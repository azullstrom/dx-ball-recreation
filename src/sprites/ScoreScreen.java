package sprites;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JList;
import javax.swing.JPanel;

import main.Constant;
import main.GameBoard;
import main.MyStack;
import main.HighScoreWindow;
import main.Key;
import main.Keyboard;
import states.GameState;

public class ScoreScreen {
	private Font font1, font2, font3;
	private GameState gameState;
	private HighScoreWindow highScoreWindow;
	private GameBoard board;
	private int tickCount, score;
	
	public ScoreScreen(GameBoard board) {
		this.board = board;
	}
	
	public void update(Keyboard keyboard) {
		if(gameState == GameState.Ending) {
			tickCount++;
		}
		if(tickCount == 1)
			highScoreWindow = new HighScoreWindow(score, board);
		if(tickCount > 1)
			highScoreWindow.update(keyboard);
	}
	
	public void draw(Graphics2D graphics, int score, GameState gameState) {
		font1 = new Font("Verdana", Font.BOLD, 20);
		font2 = new Font("Verdana", Font.BOLD, 20);
		font3 = new Font("Verdana", Font.BOLD, 30);
		
		if(gameState != GameState.Ending) {
			graphics.setFont(font1);
			graphics.setColor(Color.pink);
			graphics.drawString("YOUR SCORE: " + score, Constant.WINDOW_WIDTH - 240, 33);
			graphics.setFont(font2);
			graphics.setColor(Color.lightGray);
			graphics.drawString("YOUR SCORE: " + score, Constant.WINDOW_WIDTH - 241, 32);
		} 
		if(gameState == GameState.Ending){
			this.gameState = gameState;
			this.score = score;
			if(tickCount > 1)
				highScoreWindow.draw(graphics, font3);
		}
	}
}

