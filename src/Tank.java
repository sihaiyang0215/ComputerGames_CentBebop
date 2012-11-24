import java.awt.Color;

public class Tank {
	double x_pos;// current tank x position according to map
	double y_pos;// current tank y position acoording to map
	double dir_hor;// current tank move direction: -1 left, 1 right
	double dir_vert;// current tank move direction: -1 backward, 1 forward
	double angle; // current moving angle of the gun
	double moveSpeed; // current moving speed of tank
	double rotSpeed; // current moving speed of gun
	boolean right;
	boolean up;
	int dir_rand_x;
	int dir_rand_y;
	
	double old_x;
	double old_y;
	
	int HP = 3;
	
	HW3 applet;
	int id;
	
	RectPiece model;

	public Tank(HW3 hw3,int id) {
		
		applet = hw3;
		x_pos = 13;
		y_pos = 22;
		dir_hor = 0;// left -1, right 1
		dir_vert = 0;// up -1,down 1
		angle = -Math.PI / 2;
		moveSpeed = 0.08;
		rotSpeed = Math.PI / 60;
		right = false;
		up = false;
		model = new RectPiece();
		model.setColor(Color.GREEN);
		model.isTank = true;
		this.id = id;
		
		old_x=x_pos;
		old_y=y_pos;
	}

	public void update(int[][] map) {
		old_x = x_pos;
		old_y = y_pos;
		double moveStep = dir_vert * moveSpeed;
		angle += dir_hor * rotSpeed;
		// keep angle between 0 and 360 degree
		while (angle < 0)
			angle += 2 * Math.PI;
		while (angle >= 2 * Math.PI)
			angle -= 2 * Math.PI;

		double newX = x_pos + Math.cos(angle) * moveStep;
		double newY = y_pos + Math.sin(angle) * moveStep;

		right = false;
		if (angle > Math.PI * 1.5 || angle < Math.PI * 0.5)
			right = true;
		up = false;
		if (angle > Math.PI)
			up = true;

		if (!collisionDetection(newX, newY, map)) {

			x_pos = newX;
			y_pos = newY;
			model.setBounds(x_pos*20-10, y_pos*20-10,20, 20);
			
		}
		// System.out.println(x_pos+" "+y_pos);
		// System.out.println(angle);

	}

	public boolean collisionDetection(double x, double y, int[][] map) {
		double tempX = 0;
		double tempY = 0;
		double increase =0.5*dir_vert;
		if (right == true)
			tempX = x + increase;
		else
			tempX = x - increase;
		if (up == true)
			tempY = y - increase;
		else
			tempY = y + increase;
		if (tempY < 1 || tempY > 23 || tempX < 1.5 || tempX > 31)// out of boundry
			return true;
		return (map[(int) Math.floor(tempY)][(int) Math.floor(tempX)] != 0);
	}

	public void fire(){

		Bullet b = new Bullet(this,(int)(x_pos*20), (int)(y_pos*20));
		b.setDir_vector(angle);
		applet.addBullet(b);
	}

	public HW3 getApplet() {
		return applet;
	}

	public void setApplet(HW3 applet) {
		this.applet = applet;
	}

	public Piece getModel(){
		return model;
	}

	public boolean isPlayer() {
		if(this.id == 100){
			return true;
		}
		return false;
	}

}
