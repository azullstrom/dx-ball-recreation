package sprites;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Keyboard;
import states.BrickState;

public class BrickInvisible extends Brick {
	private BrickState brickState;

	public BrickInvisible(int x, int y, int width, int height, Color edgeColor, Color midColor) {
		super(x, y, width, height, edgeColor, midColor);
	}
	
	@Override
	public void update(Keyboard keyboard) {
		brickState = super.getBrickState();
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		if(brickState != BrickState.NotHit)
			super.draw(graphics);
	}
}
