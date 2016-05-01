import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Player {

	private double[] x;
	private double[] y;
	int xSpeed;
	int ySpeed;
	private double angle;
	int lives;
	boolean gameOver = false;
	boolean canFire = true;
	int waitFire = 0;
	int respawnInvcibility = 30;
	float alpha = 0.00f;
	int score;

	public Player() {
		x = new double[3];
		y = new double[3];
		x[0] = 490;
		x[1] = 500;
		x[2] = 510;
		y[0] = 350;
		y[1] = 325;
		y[2] = 350;
		angle = Math.PI / 2;
	}

	public void drawPlayer(Graphics g) {
		int[] x = new int[3];
		int[] y = new int[3];
		for (int i = 0; i < 3; i++) {
			x[i] = (int) this.x[i];
			y[i] = (int) this.y[i];
		}
		alpha += 0.05f;
		if (alpha >= 1.0f) {
			alpha = 1.0f;
		}

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(0, 1, 0, alpha));

		// wrap around
		int[] xNew = new int[3];
		int[] yNew = new int[3];
		for (int i = 0; i < 3; i++) {
			boolean wrap = false;
			if (x[i] > 1000) {
				xNew[0] = x[0] - 1000;
				xNew[1] = x[1] - 1000;
				xNew[2] = x[2] - 1000;
				yNew[0] = y[0];
				yNew[1] = y[1];
				yNew[2] = y[2];
				wrap = true;

			}
			if (x[i] < 0) {
				xNew[0] = x[0] + 1000;
				xNew[1] = x[1] + 1000;
				xNew[2] = x[2] + 1000;
				yNew[0] = y[0];
				yNew[1] = y[1];
				yNew[2] = y[2];
				wrap = true;
			}
			if (y[i] > 700) {
				xNew[0] = x[0];
				xNew[1] = x[1];
				xNew[2] = x[2];
				yNew[0] = y[0] - 700;
				yNew[1] = y[1] - 700;
				yNew[2] = y[2] - 700;
				wrap = true;

			}
			if (y[i] < 0) {
				xNew[0] = x[0];
				xNew[1] = x[1];
				xNew[2] = x[2];
				yNew[0] = y[0] + 700;
				yNew[1] = y[1] + 700;
				yNew[2] = y[2] + 700;
				wrap = true;
			}
			if (wrap) {
				g.drawPolygon(xNew, yNew, 3);
			}

		}

		g2.drawPolygon(x, y, 3);
	}
	
	
	public void drawScore(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		String scoreStr = "Score: " + String.valueOf(score);
		g.drawString(scoreStr, 100, 50);
	}
	
	public void drawLives(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		String scoreStr = "Lives: " + String.valueOf(lives);
		g.drawString(scoreStr, 100, 75);
	}

	public void rotate(double dangle) {
		angle = angle + dangle;
		// System.out.println(angle);
		double s = Math.sin(dangle);
		double c = Math.cos(dangle);
		// translate point back to origin:
		double cx = (x[0] + x[1] + x[2]) / 3;
		double cy = (y[0] + y[1] + y[2]) / 3;

		for (int i = 0; i < 3; i++) {
			x[i] -= cx;
			y[i] -= cy;

			double xnew = x[i] * c - y[i] * s;

			double ynew = x[i] * s + y[i] * c;

			x[i] = (xnew + cx);
			y[i] = (ynew + cy);
		}

	}

	public void moveForward(double moveSpeed) {
		for (int i = 0; i < 3; i++) {
			x[i] = x[i] + Math.cos(angle) * moveSpeed;
			y[i] = y[i] + Math.sin(angle) * moveSpeed;
			double cx = (x[0] + x[1] + x[2]) / 3;
			double cy = (y[0] + y[1] + y[2]) / 3;
			if (cx > 1000) {
				for (int j = 0; j < 3; j++) {
					x[j] = x[j] - 1000;
				}
			}
			if (cx < 0) {
				for (int j = 0; j < 3; j++) {
					x[j] = x[j] + 1000;
				}
			}
			if (cy > 700) {
				for (int j = 0; j < 3; j++) {
					y[j] = y[j] - 690;
				}
			}
			if (cy < 0) {
				for (int j = 0; j < 3; j++) {
					y[j] = y[j] + 700;
				}
			}

		}
	}

	public boolean detectCollision(double x2, double y2, int radius) {
		for (int i = 0; i < 3; i++) {
			if (x[i] > x2 && x[i] < x2 + radius && y[i] < y2 + radius
					&& y[i] > y2) {
				return true;
			}
		}
		return false;
	}

	public void resetPlayer() {
		alpha = 0;
		respawnInvcibility = 30;
		x = new double[3];
		y = new double[3];
		x[0] = 490;
		x[1] = 500;
		x[2] = 510;
		y[0] = 350;
		y[1] = 325;
		y[2] = 350;
		angle = Math.PI / 2;
		if (lives == 0) {
			gameOver = true;
		}
	}

	public double[] getX() {
		return x;
	}

	public double[] getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}

}
