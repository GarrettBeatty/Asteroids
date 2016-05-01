import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main  {
	
	static JFrame main = new JFrame("Asteroids");

	public static void main(String[] args) {
		
		Image image = null;
		JFrame jFrame = new JFrame("Asteroids");
		jFrame.setUndecorated(true);
		
		// Load image object. File should be in the "src" folder
		try {
			image = ImageIO.read((new File("splash.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		SplashPanel splashPanel = new SplashPanel(image);
		
		jFrame.add(splashPanel);
		
		//*** Follow the directions for the behavior and display of the splash panel
		jFrame.setVisible(true);
		jFrame.setSize(640,446);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 try {
			Thread.sleep(2000);
			splashPanel.setVisible(false);
			jFrame.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		 main.setSize(1000, 700);
		 Level level = new Level();
		 level.setSize(1000,700);
		 main.add(level);
		 main.addKeyListener(level);

		 main.setVisible(true);
		 main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 main.setResizable(false);
		 main.setFocusable(true);

	}

	

}
