package sprites;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class PlayerLifeScreen {
	private ArrayList<PlayerLifeBall> playerLives;
	public PlayerLifeScreen() {
		playerLives = new ArrayList<PlayerLifeBall>();
		for(int i = 0; i < 3; i++)
			playerLives.add(new PlayerLifeBall(10 + 30 * i, 10, 20, 20));
	}

	public void update(int life) {
		if(life == 2) {
			playerLives.remove(2);
		}
		if(life == 1) {
			playerLives.remove(1);
		}
		if(life == 0) {
			playerLives.remove(0);
		}
	}

	public void draw(Graphics2D graphics) {
		for(PlayerLifeBall lifeBall : playerLives) {
			lifeBall.draw(graphics);
		}
	}
}
