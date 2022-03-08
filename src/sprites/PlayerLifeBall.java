package sprites;
import java.awt.Color;
import java.awt.Graphics2D;

import main.Keyboard;

public class PlayerLifeBall extends Sprite {

	public PlayerLifeBall(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Keyboard keyboard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.pink);
		graphics.fillOval(getX(), getY(), getWidth(), getHeight());
		graphics.setColor(Color.lightGray);
		graphics.fillOval(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4);
		graphics.setColor(Color.darkGray);
		graphics.fillOval(getX() + 4, getY() + 4, getWidth() - 8, getHeight() - 8);
		graphics.setColor(Color.gray);
		graphics.fillOval(getX() + 6, getY() + 6, getWidth() - 12, getHeight() - 12);
	}

}
