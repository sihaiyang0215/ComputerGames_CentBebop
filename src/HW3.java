import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class HW3 extends GamePlatform {

	double fov = Math.PI / 3; // 60 degree
	int raysNum = 160;
	// boolean showRays =true;
	double viewDist = 320 / Math.tan((fov / 2));// distance between camera and
	
	Random random;
	// view canvas
	// 32*24 map matrix
	// 0 - nothing
	// 1 - world boundry
	// 2 - brick wall
	// 3 - stone wall
	// 4 - home
	List<Bullet> bulltes = new ArrayList<Bullet>();
	List<Tank> enemies = new ArrayList<Tank>();
	Set<Bullet> firedBullets = new TreeSet<Bullet>();
	
	MediaTracker mt;
	URL base;
	Image wall1,wall2,wall3,wall4;
	private AudioClip backgroundAudio;
	
//	int NUM_enemy = playedLevel * 2;

	int[][][] level1Map = {
		      {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,3,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,1},
		      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}},
		      
		      {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,3,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,1},
		      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}},
		      
		      {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
		      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,3,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,1},
		      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,1},
		      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}}
		  } ;

	Tank player = new Tank(this, 100);

//	Tank e_1 = new Tank(this, 2);
//	Tank e_2 = new Tank(this, 3);
	
	Color playerColor = new Color(0, 255, 0);
	Color enemyColor = new Color(255, 0, 0);

	@Override
	public boolean keyDown(Event e, int key) {
		if(gameover == false && playing == true){
			if(backgroundAudio == null)
				enableAudio();
		}
		switch (key) {
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
		case 32:
			player.fire();
			break;
		}
		return super.keyDown(e, key);
	}

	@Override
	public boolean keyUp(Event e, int key) {
		switch (key) {
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
		}
		return super.keyUp(e, key);
	}

	@Override
	public void drawMap(Graphics g) {
		if(playedLevel <= 3){
		for (int j = 0; j < 24; j++) {
			for (int i = 0; i < 32; i++) {
				switch (level1Map[playedLevel - 1][j][i]) {
				case 1:
					if(wall1 == null){
						wall1 = intializeImage("wall2.jpg");
					}
					g.drawImage(wall1,i * 20, j * 20, 20, 20,this);
					break;
				case 2:
					if(wall2 == null){
						wall2 = intializeImage("wall3.jpg");
					}
					g.drawImage(wall2,i * 20, j * 20, 20, 20,this);
					break;
				case 3:
					if(wall3 == null){
						wall3 = intializeImage("wall1.jpg");
					}
					g.drawImage(wall3,i * 20, j * 20, 20, 20,this);
					break;
				case 4:
					if(wall4 == null){
						wall4 = intializeImage("wall4.jpg");
					}
					g.drawImage(wall4,i * 20, j * 20, 20, 20,this);
					break;

				}
			}
		}

		g.setColor(Color.WHITE);
		
		g.drawLine((int) (player.x_pos * 20), (int) (player.y_pos * 20),
				(int) ((player.x_pos + Math.cos(player.angle) * 1) * 20),
				(int) ((player.y_pos + Math.sin(player.angle) * 1) * 20));
		for (Tank tank : enemies) {
			tank.model.setColor(Color.red);
			g.drawLine((int) (tank.x_pos * 20), (int) (tank.y_pos * 20),
				(int) ((tank.x_pos + Math.cos(tank.angle) * 1) * 20),
				(int) ((tank.y_pos + Math.sin(tank.angle) * 1) * 20));
		}
	}
	}

	@Override
	public void update() {
		if (playedLevel <= 3) {
			if (enemies.size() == 0) {
				playing = false;
				disableAudio();
				backgroundAudio = null;
				playedLevel++;

				initGM();
				stop();
			}

			updateEnemies();

			for (int i = 0; i < bulltes.size(); i++) {
				Bullet bullet = bulltes.get(i);
				if (!bullet.update(level1Map[playedLevel - 1]))
					continue;
				bullet.setBounds(bullet.getCurrentX(), bullet.getCurrentY(),
						bullet.getSize(), bullet.getSize());
				if (!firedBullets.contains(bullet)) {

					bullet.setColor(Color.RED);
					addPiece(bullet);
					firedBullets.add(bullet);
				}
			}
			for (Bullet bullet : firedBullets) {
				// please use list to store tank objects.
				// then traverse the list
				if (CD(player, bullet))
					if (bullet.getOwner().isPlayer())
						
						continue;
					else {
						player.HP--;
						removeBullet(bullet);
						firedBullets.remove(bullet);
						System.out.println(player.HP);
						if (player.HP == 0) {
							playing = false;
						}
						break;
					}

				for (int i = 0; i < enemies.size(); i++) {
					Tank tank = enemies.get(i);
					if (CD(tank, bullet))
						if (bullet.getOwner().isPlayer()) {
							// do minus e_1's HP
							removeBullet(bullet);
							firedBullets.remove(bullet);
							removePiece(tank.getModel());
							enemies.remove(tank);
							System.out.println("enemy " + tank.id + " hit");
							break;
						} else
							continue;
				}

			}
		}
	}
	
	private void updateEnemies() {
		for (Tank tank : enemies) 
		{	
			double dx = player.x_pos - tank.x_pos;
			double dy = player.y_pos - tank.y_pos;
			tank.dir_vert = 1;
			int dir_rand = random.nextInt();
			
			boolean notMove_X=Math.abs(tank.old_x-tank.x_pos)<0.01;
			boolean notMove_Y=Math.abs(tank.old_y-tank.y_pos) <0.01;
			
			if(dir_rand%100>95)
				tank.fire();
			if(notMove_X&&notMove_Y)
			{
				tank.dir_rand_x = dir_rand;
				if(tank.dir_rand_x>0)
				{
					if(dx>0)
						tank.angle = 2*Math.PI;
					else
						tank.angle = Math.PI;
				}
				else
				{
					if(dy>0)
						tank.angle = Math.PI/2;
					else
						tank.angle = Math.PI*3/2;
				}
			}
		}
	}

	public boolean CD(Tank tank, Bullet bullet)
	{
		double dx = tank.x_pos*20 - bullet.x;
		double dy = tank.y_pos*20 - bullet.y;
		
		double d = Math.sqrt(dx*dx+ dy*dy);
		if(d < 20)//player's size
		{
			return true;
		}
		return false;
	}

	public void addBullet(Bullet b) {
		bulltes.add(b);
	}

	public void removeBullet(Bullet b) {
		for (int i =0;i< bulltes.size();i++) 
		{
			if(bulltes.get(i) == b)
				bulltes.remove(i);
		}
		if(firedBullets.contains(b))
			firedBullets.remove(b);
		removePiece(b);
	}

	@Override
	public void drawWorld(Graphics g) {
		if (playedLevel <= 3) {
			player.update(level1Map[playedLevel - 1]);
			for (Tank tank : enemies) {
				tank.update(level1Map[playedLevel - 1]);
			}
		}
	}

	public void setup() {
		
		random = new Random(System.currentTimeMillis()); 
		int NUM_enemy = playedLevel * 2;
		Tank e[] = new Tank[NUM_enemy];
		for(int i = 0; i < NUM_enemy; i++){
			e[i] = new Tank(this, i);
			addPiece(e[i].model);
			e[i].x_pos = 5 + 2 * i;
			e[i].y_pos = 14;
			enemies.add(e[i]);
		}
		
		addPiece(player.model);
		player.HP = 3;
		player.x_pos = 13;
		player.y_pos = 22;
	}

	public void initGM(){
		bulltes.clear();
		enemies.clear();
		firedBullets.clear();
		removeAllPiece();
		setup();
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
	
	Image intializeImage(String imagename){

		// initialize the MediaTracker 
		mt = new MediaTracker(this);

		// The try-catch is necassary when the URL isn't valid 
		// Ofcourse this one is valid, since it is generated by 
		// Java itself.

		try { 
			base = getDocumentBase();

		} 
		catch (Exception e) {}

		// Here we load the image. 
		Image backgroundImage = getImage(base,imagename);
		// tell the MediaTracker to keep an eye on this image, and give it ID 1; 
		mt.addImage(backgroundImage,100);

		// now tell the mediaTracker to stop the applet execution 
		// (in this example don't paint) until the 4 are fully loaded. 
		// must be in a try catch block.

		try { 
			mt.waitForAll(); 
		} 

		catch (InterruptedException  e) {}

		return backgroundImage;

	}
	
	public void enableAudio()
	{
		if(this.backgroundAudio == null)
		{
			this.backgroundAudio = this.getAudioClip(base,
			"fire.wav");
		}

		this.backgroundAudio.loop();
	}


	public void disableAudio()
	{
		if (this.backgroundAudio != null) {
			this.backgroundAudio.stop();
		}
	}
	
	public void overlay(Graphics g) {
		if(playing == true){
		g.setColor(Color.GREEN);
		g.drawString("HP LEFT: " + player.HP, 40, 50);
		}
		if(player.HP == 0){

			disableAudio();
		      backgroundAudio = null;
		      initGM();
		      g.setColor(Color.black);
		      g.fillRect(0, 0, w, h);
		      g.setColor(Color.white);
		      g.setFont(labelFont);
		      g.drawString("GAME OVER", w / 2 - 80, h / 2);
		      g.drawString("Click to restart game", w / 3 - 10, h - 30);
			  gameover = true;
			  playing = false;
		      stop();
		}
		
		if(playedLevel > 3){
			g.setColor(Color.black);
			g.fillRect(0, 0, w, h);
			g.setColor(Color.white);
			g.setFont(labelFont);
			g.drawString("YOU WIN!!!!! " , w / 2 - 40, h / 2);
		}
	}
}
