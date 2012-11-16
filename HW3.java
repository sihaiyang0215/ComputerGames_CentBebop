import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HW3 extends GamePlatform {

	double fov = Math.PI / 3; // 60 degree
	int raysNum = 160;
	// boolean showRays =true;
	double viewDist = 320 / Math.tan((fov / 2));// distance between camera and
	// view canvas
	// 32*24 map matrix
	// 0 - nothing
	// 1 - world boundry
	// 2 - brick wall
	// 3 - stone wall
	// 4 - home
	// Color boundryColor = new Color(150,150,150,100);
	// Color brickColor = new Color(178,34,34,100);
	// Color stoneColor = new Color(250,250,250,100);
	// Color homeColor = new Color(255,215,5,100);
	// For temp use
	// Color boundryColor1 = new Color(150,150,150,100);
	// Color brickColor1 = new Color(186,34,34);
	// Color stoneColor1 = new Color(250,250,250,100);

	List<Bullet> bulltes = new ArrayList<Bullet>();
	Set<Bullet> firedBullets = new TreeSet<Bullet>();

	int[][] level1Map = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
						{ 1, 0, 0, 0, 2, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 2,
							2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
							{ 1, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 2,
								2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
								{ 1, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 2,
									2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
									{ 1, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 2,
										2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
										{ 1, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 2,
											2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
											{ 1, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 2,
												2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
												{ 1, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 2,
													2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
													{ 1, 0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2,
														2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
														{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
															2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
															{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
																2, 0, 0, 0, 0, 2, 2, 0, 0, 1 },
																{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 3, 3, 0, 2, 2, 0, 0,
																	0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
																	{ 1, 0, 0, 0, 0, 0, 2, 2, 0, 3, 3, 3, 0, 0, 0, 3, 3, 0, 2, 0, 0, 0,
																		0, 0, 0, 3, 3, 0, 0, 0, 0, 1 },
																		{ 1, 0, 0, 0, 2, 2, 2, 2, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0,
																			0, 0, 0, 3, 3, 0, 0, 0, 0, 1 },
																			{ 1, 0, 0, 0, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0, 2, 0, 0, 2,
																				2, 0, 0, 3, 3, 0, 0, 0, 0, 1 },
																				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
																					2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
																					{ 1, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
																						2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
																						{ 1, 0, 0, 0, 3, 3, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
																							2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
																							{ 1, 0, 0, 0, 3, 3, 0, 0, 0, 2, 2, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 2,
																								2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
																								{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 4, 4, 2, 0, 0, 0, 2,
																									2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
																									{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 4, 4, 2, 0, 0, 0, 2,
																										2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
																										{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
																											1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	// int[][] level1Map = {
	// {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	// {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	// } ;

	Tank player = new Tank(this, 1);
	Color playerColor = new Color(0, 255, 0, 150);

	@Override
	public boolean keyDown(Event e, int key) {
		switch (key) {
		// case 109:
		// showMiniMap = true;
		// break;
		case 119:
			player.dir_vert = 1;
			break;
		case 115:
			player.dir_vert = -1;
			break;
		case 100:
			player.dir_hor = 1;
			break;
		case 97:
			player.dir_hor = -1;
			break;
			// case 110:
			// showRays = true;
			// showMiniMap = true;
			// break;
			// case 98:
			// showAim = true;
			// break;
		case 32:
			player.fire();
			break;
		}
		return super.keyDown(e, key);
	}

	@Override
	public boolean keyUp(Event e, int key) {
		switch (key) {
		// case 109:
		// // showMiniMap = false;
		// break;
		case 119:
			player.dir_vert = 0;
			break;
		case 115:
			player.dir_vert = 0;
			break;
		case 100:
			player.dir_hor = 0;
			break;
		case 97:
			player.dir_hor = 0;
			break;
			// case 110:
			// showRays = false;
			// showMiniMap = false;
			// break;
			// case 98:
			// showAim = false;
			// break;
		}
		return super.keyUp(e, key);
	}

	@Override
	public void drawMap(Graphics g) {

		for (int j = 0; j < 24; j++) {
			for (int i = 0; i < 32; i++) {
				switch (level1Map[j][i]) {
				case 1:
					// g.setColor(boundryColor);
					g.setColor(Color.DARK_GRAY);
					g.fillRect(i * 20, j * 20, 20, 20);
					break;
				case 2:
					// g.setColor(brickColor);
					g.setColor(Color.RED);
					g.fillRect(i * 20, j * 20, 20, 20);
					break;
				case 3:
					// g.setColor(stoneColor);
					g.setColor(Color.WHITE);
					g.fillRect(i * 20, j * 20, 20, 20);
					break;
				case 4:
					// g.setColor(homeColor);
					g.setColor(Color.YELLOW);
					g.fillRect(i * 20, j * 20, 20, 20);
					break;
				}
			}
		}

		// player.update(level1Map);

		g.setColor(playerColor);
		g.fillRect((int) (player.x_pos * 20) - 10,
				(int) (player.y_pos * 20) - 10, 20, 20);
		g.setColor(Color.WHITE);
		g.drawLine((int) (player.x_pos * 20), (int) (player.y_pos * 20),
				(int) ((player.x_pos + Math.cos(player.angle) * 1) * 20),
				(int) ((player.y_pos + Math.sin(player.angle) * 1) * 20));
	}

	@Override
	public void update() {

		// castRays(g, level1Map, false);

		for (Bullet bullet : bulltes) {

			bullet.update(level1Map);
			bullet.setBounds(bullet.getCurrentX(), bullet.getCurrentY(),
					bullet.getSize(), bullet.getSize());
			if (!firedBullets.contains(bullet)) {

				bullet.setColor(Color.RED);
				addPiece(bullet);
				firedBullets.add(bullet);
			}
		}
	}

	public void addBullet(Bullet b) {
		bulltes.add(b);
		//setCollisionTracked(b);
	}

	public void removeBullet(Bullet b) {
		removePiece(b);
	}

	// cast a ray from camera and test its distance to the nearest obstacle
	public void castRay(double angle, int[][] map, Graphics g, int rayNum) {
		// clip angle into 0 to 360 degree
		angle %= Math.PI * 2;
		if (angle < 0)
			angle += Math.PI * 2;

		// detect direction
		boolean right = false;
		if (angle > Math.PI * 1.5 || angle < Math.PI * 0.5)
			right = true;
		boolean up = false;
		if (angle > Math.PI)
			up = true;

		int textureIndex = 0;
		double dist = 0; // the distance to the obstacle
		double distX = 0; // the x distance to obstacle
		double distY = 0; // the y distance to obstacle
		double xHit = 0; // the x, y cordinate of obstacle
		double yHit = 0;

		// double tanAngle = Math.sin(angle)/Math.cos(angle);
		double tanAngle = Math.tan(angle);
		double dx = right ? 1 : -1;
		double dy = dx * tanAngle;

		double start_x = right ? Math.ceil(player.x_pos) : Math
				.floor(player.x_pos);
		double start_y = player.y_pos + (start_x - player.x_pos) * tanAngle;

		double x = 0;// temp x, y in order to test obstacle
		double y = 0;

		// loop to find the obstacle
		while (start_x >= 0 && start_x < 32 && start_y >= 0 && start_y < 24) {
			if (right == false) {
				x = Math.floor(start_x) - 1;
			} else
				x = Math.floor(start_x);
			y = Math.floor(start_y);

			// System.out.println(y+" "+x+" "+map[(int)y][(int)x]);

			// is this point inside a wall block?
			if (map[(int) y][(int) x] > 0) {
				distX = start_x - player.x_pos;
				distY = start_y - player.y_pos;
				dist = distX * distX + distY * distY; // the squared distance
				// from the player to
				// this point

				// save the coordinates of the hit.
				xHit = start_x;
				yHit = start_y;
				textureIndex = map[(int) y][(int) x];
				break;
			}
			start_x += dx;
			start_y += dy;
		}

		// now check against horizontal lines.
		// to get cot(angle)
		if (tanAngle != 0)
			tanAngle = 1 / tanAngle;
		dy = up ? -1 : 1;
		dx = dy * tanAngle;
		start_y = up ? Math.floor(player.y_pos) : Math.ceil(player.y_pos);
		start_x = player.x_pos + (start_y - player.y_pos) * tanAngle;
		double blockDist = 0;

		while (start_x >= 0 && start_x < 32 && start_y >= 0 && start_y < 24) {
			if (up == true)
				y = (int) Math.floor(start_y) - 1;
			else
				y = (int) Math.floor(start_y);
			x = (int) Math.floor(start_x);
			if (map[(int) y][(int) x] > 0) {
				distX = start_x - player.x_pos;
				distY = start_y - player.y_pos;
				blockDist = distX * distX + distY * distY;
				if (dist == 0 || blockDist < dist) {
					dist = blockDist;
					xHit = start_x;
					yHit = start_y;
					textureIndex = map[(int) y][(int) x];
				}
				break;
			}
			start_x += dx;
			start_y += dy;
		}

		if (dist > 0) {

			dist = Math.sqrt(dist);
			dist = dist * Math.cos(player.angle - angle);

			// double height = Math.round(viewDist / dist);
			// double top = Math.round((480 - height) / 2);
			// switch(textureIndex){
			// case 1:
			// g.setColor(Color.darkGray);
			// break;
			// case 2:
			// g.setColor(brickColor1);
			// break;
			// case 3:
			// g.setColor(Color.gray);
			// break;
			// }
			// g.fillRect(rayNum*4, (int)top, (int)4, (int)height);
			// show casting rays on mini map
			// if(showRays == true){
			// g.setColor(new Color(100,200,100,100));
			// g.drawLine((int)(player.x_pos* 20), (int)(player.y_pos*20),
			// (int)Math.round(xHit*20), (int)Math.round(yHit*20));
			// }
		}
	}

	@Override
	public void drawWorld(Graphics g) {
		player.update(level1Map);

		// castRays(g, level1Map,false);
	}

	// public void castRays(Graphics g, int[][] map, boolean showRays) {
	// double rayScreenPos=0;
	// double rayAngle=0;
	// int rayNum = 0;
	// for (int i=0;i<raysNum;i++) {
	// rayScreenPos = (i-raysNum/2) * (640/raysNum);
	// rayAngle = Math.atan(rayScreenPos/ viewDist);
	// castRay(player.angle + rayAngle, map, g, rayNum);
	// rayNum ++;
	// }
	// }

	public void setup() {
		//setCollisionTracked(player.getModel());
		addPiece(player.model);
	}

	public void startCollision(Piece one, Piece two) {


		if(!one.isTank && !two.isTank)
		{
			//delete bullet from bulletlist
			return;
		}
		//one is tank; two is bullet
		if(one.isTank && !two.isTank)
		{
			Tank tank = getTank(one);
			if(tank.isPlayer() && !getBullet(two).getOwner().isPlayer())
			{
				//minus hp of player
				return;
			}
			if(getBullet(two).getOwner().isPlayer())
			{
				//player hit the enemy
				//minus hp of tank
				return;
			}


			return;
		}

		//two is tank; one is bullet
		if(two.isTank && !one.isTank)
		{
			Tank tank = getTank(two);
			if(tank.isPlayer() && !getBullet(one).getOwner().isPlayer())
			{
				//minus hp of player
				return;
			}
			if(getBullet(one).getOwner().isPlayer())
			{
				//player hit the enemy
				//minus hp of tank
				return;
			}
		}
		
	}

	private Bullet getBullet(Piece one) {
		for (Bullet b : bulltes) {
			if (one == b) {
				return b;
			}
		}
		return null;
	}

	private Tank getTank(Piece one) {
		if(one == player.model){
			return player;
		}
		return null;
	}

}
