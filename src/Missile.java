import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile {
	private int x, y;
	private static final int MISSLE_SIZE = 16;
	private static final int MISSLE_RADIUS = MISSLE_SIZE >> 1;
	private boolean alive = true;
	private Direction dir = null;
	private static final int STEP_SIZE = 15;
	
	
	public Missile(int x, int y, Direction dir) {
		this.x = x+ Tank.TANK_RADIUS - MISSLE_RADIUS;
		this.y = y+ Tank.TANK_RADIUS - MISSLE_RADIUS;
		this.dir = dir;
	}
	
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.orange);
		g.fillOval(x, y, MISSLE_SIZE, MISSLE_SIZE);
		g.setColor(c);
		move();
	}

	public boolean isAlive() {
		if(x > TankClient.FRAME_WIDTH || x < 0 || 
				y > TankClient.FRAME_HEIGHT || y < 0) 
			alive = false;
		return alive;	
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void move(){
		switch(dir){
		case U:
			y -= STEP_SIZE;
			break;
		case RU:
			y -= STEP_SIZE;
			x += STEP_SIZE;
			break;
		case R:
			x += STEP_SIZE;
			break;
		case RD:
			y += STEP_SIZE;
			x += STEP_SIZE;
			break;
		case D:
			y += STEP_SIZE;
			break;
		case LD:
			y += STEP_SIZE;
			x -= STEP_SIZE;
			break;
		case L:
			x -= STEP_SIZE;
			break;
		case LU:
			y -= STEP_SIZE;
			x -= STEP_SIZE;
			break;
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,MISSLE_SIZE,MISSLE_SIZE);
	}
	
	public boolean hitTank(Tank tank)
	{
		if(this.getRect().intersects(tank.getRect()))
			return true;
		return false;
	}
	

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
