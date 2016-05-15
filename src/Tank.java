import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tank {
	
	private int x, y;
    public static final int TANK_SIZE = 30;
    public static final int TANK_RADIUS = TANK_SIZE >> 1;
    private boolean bU = false, bD = false, bL = false, bR = false;
    private boolean isStop = true;
    private Direction dir = Direction.R;
    private static final int STEP_SIZE = 10;
    private List<Missile> missiles = new ArrayList<Missile>();
    private boolean good;
    private boolean alive = true;
    private TankClient tc;
    private List<Explode> eps = new ArrayList<Explode>();
    // constructor
	public Tank(int x, int y, boolean good, TankClient tc) {
		this.x = x;
		this.y = y;
		this.good = good;
		this.tc = tc;
	}
	
	
	// paint tank on frame
	public void paint(Graphics g){
		if(!alive) return;
		Color c = g.getColor();
		// paint tank
		if(good)
			g.setColor(Color.RED);
		else
			g.setColor(Color.GREEN);
		g.fillOval(x, y, TANK_SIZE, TANK_SIZE);
		
		// paint all missiles
		Iterator<Missile> itMissile = missiles.iterator();
		while(itMissile.hasNext()){
			Missile missile = itMissile.next();
			Tank enemy = tc.getEnemyTank();
			if(enemy.isAlive()&&missile.hitTank(enemy)){
				eps.add(new Explode(missile.getX(),missile.getY()));
				missile.setAlive(false);
				enemy.setAlive(false);
			}
				
			if(missile.isAlive())
				missile.paint(g);
			else{
				itMissile.remove();
				missile = null;
			}
		}
		
		// paint all explosions
		Iterator<Explode> itExplode = eps.iterator();
		while(itExplode.hasNext()){
			Explode e = itExplode.next();
			if(e.isAlive())
				e.draw(g);
			else{
				itExplode.remove();
				e = null;
			}
		}
		
		
		drawGun(g);
		g.setColor(c);
	}
	
	public boolean isAlive() {
		return alive;
	}


	public void setAlive(boolean live) {
		this.alive = live;
	}


	public void drawGun(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.black);
		switch(dir){
		case U:
			g.drawLine(x + TANK_RADIUS, y + TANK_RADIUS,x + TANK_RADIUS , y);
			break;
		case RU:
			g.drawLine(x + TANK_RADIUS, y + TANK_RADIUS,x + TANK_SIZE , y);
			break;
		case R:
			g.drawLine(x + TANK_RADIUS, y + TANK_RADIUS,x + TANK_SIZE , y + TANK_RADIUS);
			break;
		case RD:
			g.drawLine(x + TANK_RADIUS, y + TANK_RADIUS,x + TANK_SIZE , y + TANK_SIZE);
			break;
		case D:
			g.drawLine(x + TANK_RADIUS, y + TANK_RADIUS,x + TANK_RADIUS , y + TANK_SIZE);
			break;
		case LD:
			g.drawLine(x + TANK_RADIUS, y + TANK_RADIUS,x , y + TANK_SIZE);
			break;
		case L:
			g.drawLine(x + TANK_RADIUS, y + TANK_RADIUS,x, y + TANK_RADIUS);
			break;
		case LU:
			g.drawLine(x + TANK_RADIUS, y + TANK_RADIUS,x , y);
			break;
		}
		g.setColor(c);
	}
	
	// set four direction flag to true when corresponding key is pressed
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			bL = true;
			isStop = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			isStop = false;
			break;
		case KeyEvent.VK_UP:	
			bU = true;
			isStop = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			isStop = false;
			break;
		
		}
		
		if(!isStop){
			//set move direction
			setDirection();
			// move the tank
			move();
		}
	}
	
	// set four direction flag to false when corresponding key is released
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:	
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_CONTROL:
			missiles.add(new Missile(x,y,dir));
			break;
		case KeyEvent.VK_W:
			// create missiles in all directions
			for(Direction d:Direction.values()){
				missiles.add(new Missile(x,y,d));
			}
			break;
		}
		// if keys of four directions are all released, the tank has stopped
		if(!bL&&!bR&&!bU&&!bD){
			isStop = true;
		}
		
	}
	
	// move the tank according to moving direction
	public void move(){
		int x_test = x;
		int y_test = y;
		switch(dir){
		case U:
			y_test -= STEP_SIZE;
			updateXY(x_test,y_test);				
			break;
		case RU:
			y_test -= STEP_SIZE;
			x_test += STEP_SIZE;
			updateXY(x_test,y_test);	
			break;
		case R:
			x_test += STEP_SIZE;
			updateXY(x_test,y_test);	
			break;
		case RD:
			y_test += STEP_SIZE;
			x_test += STEP_SIZE;
			updateXY(x_test,y_test);	
			break;
		case D:
			y_test += STEP_SIZE;
			updateXY(x_test,y_test);	
			break;
		case LD:
			y_test += STEP_SIZE;
			x_test -= STEP_SIZE;
			updateXY(x_test,y_test);	
			break;
		case L:
			x_test -= STEP_SIZE;
			updateXY(x_test,y_test);	
			break;
		case LU:
			y_test -= STEP_SIZE;
			x_test -= STEP_SIZE;
			updateXY(x_test,y_test);	
			break;
		}
	}
	// set direction of moving
	public void setDirection(){
		if(bU && !bD && !bL && !bR) dir = Direction.U;
		else if(bU && !bD && !bL && bR) dir = Direction.RU;
		else if(!bU && !bD && !bL && bR) dir = Direction.R;
		else if(!bU && bD && !bL && bR) dir = Direction.RD;
		else if(!bU && bD && !bL && !bR) dir = Direction.D;
		else if(!bU && bD && bL && !bR) dir = Direction.LD;
		else if(!bU && !bD && bL && !bR) dir = Direction.L;
		else if(bU && !bD && bL && !bR) dir = Direction.LU;
	}
	
	// check if tank is out of frame
	public boolean isOutBoundary(int x_test, int y_test){
		if(x_test > TankClient.FRAME_WIDTH-TANK_SIZE || x_test < 0 ||
				y_test > TankClient.FRAME_HEIGHT - TANK_SIZE || y_test < 30 )
			return true;
		return false;
	}
	
	// if tank will not be out of frame, move it
	public void updateXY(int x_test, int y_test){
		if(isOutBoundary(x_test,y_test))
			return;
		else{
			this.x = x_test;
			this.y = y_test;
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,TANK_SIZE,TANK_SIZE);
	}

}
