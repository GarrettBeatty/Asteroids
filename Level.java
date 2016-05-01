import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Level extends JPanel implements KeyListener, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Asteroid> asteroids;
	Player player;
	Missile missile;
	ArrayList<Missile> missiles = new ArrayList<Missile>();
	int level = 1;
	int numAsteroids;
	Timer timer;
	double rotateSpeed = 0;
	double moveSpeed = 0;
	boolean moving;
	
	
	public Level(){
		player = new Player();
		player.lives = 3;
		makeAsteroids();
		setBackground(Color.black);
		repaint();
		addKeyListener(this);
		timer = new Timer(1000/30, this);
		timer.start();
	}
	
	 public void paintComponent(Graphics g) {
	        super.paintComponent(g);  
	        player.drawPlayer(g);
			player.drawScore(g);
			player.drawLives(g);
	        for(Asteroid a : asteroids){
	        	a.drawAsteroid(g);
	        	//System.out.println(i);
	        }
	        for(Missile m : missiles){
	        	m.drawMissile(g);
	        }
	        drawLevel(g);
	       
	 }
	 
	 public void makeAsteroids(){
		 numAsteroids = 2 + level * 2;
		 asteroids = new ArrayList<Asteroid>();
		 Random random = new Random();
		 for(int i = 0; i < numAsteroids; i++){
			 int x = random.nextInt(1000);
			 int y = random.nextInt(700);
			 double direction = random.nextInt(360);
			 direction = Math.toRadians(direction);
			 double xSpeed = Math.cos(direction) * 6;
			 double ySpeed = Math.sin(direction) * 6;			 
			 Asteroid asteroid = new Asteroid(x,y,xSpeed,ySpeed,Asteroid.BreakLevel.LEVELTHREE);
			 asteroids.add(asteroid);
		 }
		
	 }

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rotateSpeed = .17;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			rotateSpeed = -.17;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			moveSpeed = -5;
			moving = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(player.canFire){
			missile = new Missile(player.getX()[1], player.getY()[1], player.getX()[1] + (Math.cos(player.getAngle()) * 9), player.getY()[1] + (Math.sin(player.getAngle()) * 9), player.getAngle());
			missiles.add(missile);
			player.canFire = false;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||  e.getKeyCode() == KeyEvent.VK_LEFT){
			rotateSpeed = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			moving = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(asteroids.size() == 0 && player.gameOver == false){
			increaseLevel();
			player.resetPlayer();
			player.lives = player.lives + 1;
			player.score = player.score + 20;
		}
		
		
		if(player.gameOver){
			 timer.stop();
			 JOptionPane.showMessageDialog(null, "Game Over", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
			 player.gameOver = false;
			 player.resetPlayer();
			 player.lives = 3;
			 player.score = 0;
			 level = 1;
			 rotateSpeed = 0;
			 moveSpeed = 0;
			 moving = false;
			 makeAsteroids();
			 timer.start();
			 
		}
		for(Asteroid a : asteroids){
			 a.moveAsteroid();
			 if(player.respawnInvcibility == 0){
				 if(player.detectCollision(a.x, a.y, a.radius)){
					 player.lives = player.lives - 1;
					 player.resetPlayer();
				 }
			 }
		 }
		
		for(int i = 0; i < asteroids.size(); i ++){
			for(int j = 0; j < missiles.size(); j++){
				if(missiles.get(j).detectCollision(asteroids.get(i).x, asteroids.get(i).y, asteroids.get(i).radius)){
					Asteroid.BreakLevel currentBreakLevel = asteroids.get(i).breakLevel;
					int index = currentBreakLevel.ordinal();
					if(index != 0){
						asteroids.add(new Asteroid(asteroids.get(i).x,asteroids.get(i).y, asteroids.get(i).xSpeed, asteroids.get(i).ySpeed * -1.3, Asteroid.BreakLevel.values()[index - 1]));
						asteroids.add(new Asteroid(asteroids.get(i).x,asteroids.get(i).y, asteroids.get(i).xSpeed, asteroids.get(i).ySpeed * 1.3, Asteroid.BreakLevel.values()[index - 1]));
						player.score = player.score + 10;
					}
					asteroids.remove(i);
					missiles.remove(j);
					i = i - 1;
					j = j - 1;
					break;
				}
			}
		}
		
		 player.rotate(rotateSpeed);
		 player.moveForward(moveSpeed);
		 if(moveSpeed < 0 && !moving){
				moveSpeed = moveSpeed + .2;
		 }
		 for(int i  = 0; i < missiles.size(); i ++){
			 missiles.get(i).moveMissile();
			 missiles.get(i).lifeSpan = missiles.get(i).lifeSpan - 1;
			 if(missiles.get(i).lifeSpan == 0){
				 missiles.remove(i);
				 i = i -1;
			 }
		 }
		 
		 if(!player.canFire){
			 player.waitFire = player.waitFire + 1;
		 }
		 if(player.waitFire == 5){
			 player.canFire = true;
			 player.waitFire = 0;
		 }
		 
		 if(player.respawnInvcibility > 0){
			 player.respawnInvcibility = player.respawnInvcibility - 1;
		 }
		 
		 repaint();
			
	}
	
	public void increaseLevel(){
		level = level + 1;
		makeAsteroids();
	}

	public void drawLevel(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		String scoreStr = "Level " + String.valueOf(level);
		g.drawString(scoreStr, 100, 100);
	}	 	 
}
