import java.applet.Applet;



public class Bullet extends DiskPiece implements Comparable<Bullet>{

	//only one of them will be active 
	double dir_vector = 0.0;// current bullet move direction: -1 left, 1 right
	public final double moveSpeed = 5; // current moving speed of bullet

	public final double size = 5; // current moving speed of bullet
	
	boolean right;
	boolean up;

	public double getSize() {
		return size;
	}

	double currentX;
	double currentY;

	Tank Owner;

	public Tank getOwner() {
		return Owner;
	}

	public void setOwner(Tank owner) {
		Owner = owner;
	}

	public Bullet(Tank tank_id,double x, double y){

		Owner = tank_id;
		currentX = x;
		currentY = y;
		
		right = false;
		up = false;
	}

	public boolean update(int[][] map) {

		double moveStepX = 0.0;
		double moveStepY = 0.0;

		double displacementX = Math.cos(dir_vector);//(int)((currentX + Math.cos(dir_vector) * 1) * 20); 
		double displacementY = Math.sin(dir_vector);//(int)((currentY + Math.sin(dir_vector) * 1) * 20);

		moveStepX = displacementX * moveSpeed;
		moveStepY = displacementY * moveSpeed;

		double newX = currentX +  moveStepX;
		double newY = currentY +  moveStepY;
		
		right = false;
		if (dir_vector > Math.PI * 1.5 || dir_vector < Math.PI * 0.5)
			right = true;
		up = false;
		if (dir_vector > Math.PI)
			up = true;

		if (!collisionDetection(newX, newY, map)) {
			
			currentX = newX;
			currentY = newY;
			return true;
		}else{
			Owner.getApplet().removeBullet(this);
			return false;
		}

	}

	private boolean collisionDetection(double newX, double newY, int[][] map) {
		
		double tempX = 0;
		double tempY = 0;
		double increase =0.5*dir_vector;
		if (right == true)
			tempX = (newX + increase)/20;
		else
			tempX = (newX - increase)/20;
		if (up == true)
			tempY = (newY - increase)/20;
		else
			tempY = (newY + increase)/20;
		if (tempY < 1 || tempY > 23 || tempX < 1.5 || tempX > 31)// out of boundry
			return true;
		return (map[(int) Math.floor(tempY)][(int) Math.floor(tempX)] != 0);
	}


	public double getCurrentX() {
		// TODO Auto-generated method stub
		return currentX;
	}

	public double getCurrentY() {
		// TODO Auto-generated method stub
		return currentY;
	}

	public double getDir_vector() {
		return dir_vector;
	}

	public void setDir_vector(double dir_vector) {
		this.dir_vector = dir_vector;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}

	public void setCurrentX(double startX) {
		this.currentX = startX;
	}

	public void setCurrentY(double startY) {
		this.currentY = startY;
	}

	@Override
	public int compareTo(Bullet b) {
		// TODO Auto-generated method stub

		return this.id - b.id;
	}
}
