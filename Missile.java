import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Missile {

	boolean alive;
	double x1;
	double y1;
	double x2;
	double y2;
	double xSpeed;
	double ySpeed;
	int lifeSpan;

	public Missile(double d, double e, double f, double g, double angle) {
		this.x1 = d;
		this.x2 = f;
		this.y1 = e;
		this.y2 = g;
		xSpeed = Math.cos(angle) * -12;
		ySpeed = Math.sin(angle) * -12;
		lifeSpan = 60;
	}

	public void drawMissile(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(2));
		int x1 = (int) this.x1;
		int x2 = (int) this.x2;
		int y1 = (int) this.y1;
		int y2 = (int) this.y2;
		g2.drawLine(x1, y1, x2, y2);
	}

	public void moveMissile() {
		x1 = (int) (x1 + xSpeed);
		y1 = (int) (y1 + ySpeed);
		x2 = (int) (x2 + xSpeed);
		y2 = (int) (y2 + ySpeed);
		if ((x1 + x2) / 2 > 1000) {
			x1 = x1 - 1000;
			x2 = x2 - 1000;
		}
		if ((y1 + y2) / 2 > 700) {
			y1 = y1 - 700;
			y2 = y2 - 700;
		}
		if ((x1 + x2) / 2 < 0) {
			x1 = x1 + 1000;
			x2 = x2 + 1000;
		}
		if ((y1 + y2) < 0) {
			y1 = y1 + 700;
			y2 = y2 + 700;
		}
	}
	
	public boolean detectCollision(double asx, double asy, int radius){
		if(x2 > asx && x2 < asx + radius && y2 > asy && y2 < asy + radius){
			return true;
		}if(x1 > asx && x1 < asx + radius && y1 > asy && y1 < asy + radius){
			return true;
		}
		return false;
	}
}
