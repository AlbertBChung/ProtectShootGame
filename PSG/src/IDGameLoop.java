package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class IDGameLoop extends JPanel implements Runnable{
	private Thread thread;
	private boolean running;
	
	private int fps;
	private int tps;
	private int width;
	private int height;
	private int counter;
	public Graphics2D graphics2D;
	private BufferedImage img;
	
	
	private static double currFPS = 120D;
	  
	public IDGameLoop(int width , int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setFocusable(false);
		requestFocus();
		
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null){
			thread = new Thread (this);
			thread.start();
		}
	}
	
	
	

	public void run() {
	
		
	//initialization
	init();	
		
	long lastTime = System.nanoTime();
	//added 2 zero
	double nsPerTick = 100000000000D / currFPS;
	int frames = 0;
	int ticks = 0;
	long lastTimer = System.currentTimeMillis();
	double deltaTime = 0;	
	
	while(running){
		long now = System.nanoTime();
		deltaTime += (now - lastTime)/nsPerTick;
		boolean shouldRender = false;
		
		while (deltaTime >= 1 ){
			ticks++;
			tick(deltaTime);
			deltaTime -=1;
			shouldRender = true;
		}
		if(shouldRender == true){
			frames++;
			render();
		}
		try {	Thread.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
		if (System.currentTimeMillis() - lastTimer >= 100){
			lastTimer += 100;
			tps = frames;
			fps = ticks;
			frames = 0;
			ticks = 0;
		}
	
	
		if(counter == 1){
			try {	Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
			
		}
		counter++;

	}
	
	
}
	
	public void init() {
		img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		graphics2D = (Graphics2D) img.getGraphics();
		running =true;
		
	}

	public void tick(double deltaTime) {
	} 
	
	public void render() {
		graphics2D.clearRect(0, 0, width, height);
	}

	

	public void clear(){
		Graphics g2 = getGraphics();
		if (img != null){
			g2.drawImage(img,0,0,null);
		}
		g2.dispose();
	}
	

}
