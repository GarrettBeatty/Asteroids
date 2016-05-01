import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Asteroid {

	boolean alive;
	double x;
	double y;
	double xSpeed;
	double ySpeed;
    int radius;
	BreakLevel breakLevel;

	public enum BreakLevel {
		LEVELONE, LEVELTWO, LEVELTHREE
	}
	

	public Asteroid(double x, double y, double xSpeed, double ySpeed, BreakLevel breakLevel) {
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.breakLevel = breakLevel;
		switch (this.breakLevel) {
		case LEVELONE:
			radius = 20;
			break;
		case LEVELTWO:
			radius = 40;
			break;
		case LEVELTHREE:
			radius = 80;
			break;
		}
	}

	public void moveAsteroid() {
		x = (int) (x + xSpeed);
		y = (int) (y + ySpeed);
		if (x > 1000) {
			x = 0;
		}
		if (y > 700) {
			y = 0;
		}
		if (x < 0) {
			x = 1000;
		}
		if (y < 0) {
			y = 700;
		}

	}

	public void drawAsteroid(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		// wrap
		int x = (int) this.x;
		int y = (int) this.y;
		if (x + radius > 1000) {
			g.drawOval(x - 1000, y, radius, radius);
		}
		if (y + radius > 700) {
			g.drawOval(x, y - 700, radius, radius);
		}
		if (x + radius < 0) {
			g.drawOval(x + 1000, y, radius, radius);
		}
		if (y + radius < 0) {
			g.drawOval(x, y + 700, radius, radius);
		}

		g2.drawOval(x, y, radius, radius);
	}

}
