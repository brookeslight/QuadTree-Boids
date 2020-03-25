package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7604959185792845431L;
	//engine
	private boolean running;
	private Thread thread;
	private JFrame frame;
	//boids
	private Boss boss;
	
	public static void main(String[] args) {
		new Main().start();
	}
	
	public synchronized void start() {
		if(this.running == true) {
			return;
		}
		this.thread = new Thread(this);
		this.thread.start();
		this.running = true;
	}
	
	public synchronized void stop() {
		try {
			this.thread.join();
			this.running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		//engine
		this.frame = new JFrame("2D Engine");
		this.setPreferredSize(new Dimension(1920, 1080));
		this.setMaximumSize(new Dimension(1920, 1080));
		this.setMinimumSize(new Dimension(1920, 1080));
		this.frame.setPreferredSize(new Dimension(1920, 1080));
		this.frame.setMaximumSize(new Dimension(1920, 1080));
		this.frame.setMinimumSize(new Dimension(1920, 1080));
		this.frame.add(this);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(true);
		this.frame.setVisible(true);
		this.frame.requestFocus();
		
		//boids
		this.boss = new Boss();
		for(int i = 0; i < 50; i++) {
			this.boss.add(new Boid((float)(Math.random()*this.getWidth()), (float)(Math.random()*this.getWidth()), this.boss));
		}
	}
	
	@Override
	public void run() {
		this.init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(this.running == true){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				this.tick();
				updates++;
				delta--;
			}
			this.render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void tick() {
		this.boss.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform af = g2d.getTransform();
		//start draw
			//bg
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			//boids
		this.boss.render(g2d);
		//end draw
		g2d.setTransform(af);
		g2d.dispose();
		bs.show();
	}

}