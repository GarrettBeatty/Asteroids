import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class SplashPanel extends JPanel {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image image;

	public SplashPanel(Image image2) {
		image = image2;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, this);
	}

}