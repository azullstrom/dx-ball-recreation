package sprites;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Keyboard;
import states.BrickState;

public class BrickThreeHit extends Brick {

	public BrickThreeHit(int x, int y, int width, int height, Color edgeColor, Color midColor) {
		super(x, y, width, height, edgeColor, midColor);
	}
}
