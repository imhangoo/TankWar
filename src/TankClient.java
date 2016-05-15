import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame {
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGHT = 600;
	private static final Image BACKGROUND = Toolkit.getDefaultToolkit().createImage("images/black.jpg");

	private Tank myTank = new Tank(300, 300, true, this);
	private Tank enemyTank = new Tank(400,400,false, this);
	
	

	Image offScreenImage = null;
	@Override
	public void paint(Graphics g) {
		myTank.paint(g);
		enemyTank.paint(g);
	}

	@Override
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = createImage(FRAME_WIDTH,FRAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.green);
		gOffScreen.drawImage(BACKGROUND, 0, 0, null);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void launchFrame() {
		// set location and size of tank frame
		setLocation(400, 300);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		// set title
		setTitle("TankWar");	
		// set window listener to close the process
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		// display the frame
		setVisible(true);
		// launch repaint thread
		new Thread(new RepaintThread()).start();
	}
	
	public Tank getEnemyTank() {
		return enemyTank;
	}

	

	public static void main(String[] args) {
		// create a new instance of TankClient, and call its launch function
		TankClient tc = new TankClient();
		tc.launchFrame();

	}

	private class RepaintThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	private class KeyMonitor extends KeyAdapter{

		public void keyPressed(KeyEvent e) { 
			myTank.keyPressed(e);
		}

		
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		
		
		
		
	}

}
