package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Keyboard;
import states.BrickState;

public class BrickTwoHit extends Brick {

	public BrickTwoHit(int x, int y, int width, int height, Color edgeColor, Color midColor) {
		super(x, y, width, height, edgeColor, midColor);
	}
}
