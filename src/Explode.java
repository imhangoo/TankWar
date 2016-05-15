import java.awt.*;
public class Explode {
	private int x, y;
	private boolean alive = true;
	// diameter of explode fire ball
	int[] diameter = {4,7,12,18,26,32,49,30,14,6};
	// index of fire ball
	int step = 0;
	
	public Explode(int x, int y){
		this.x = x;
		this.y =y;
	}
	public void draw(Graphics g){		
		if(step==diameter.length){
			alive = false;
			step = 0;
			return;
		}
		
		// draw the fire ball one by one
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step],diameter[step]);
		g.setColor(c);
		step++;
	}
	public boolean isAlive() {
		return alive;
	}
}
