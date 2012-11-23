import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;


public class HW3_1 extends GamePlatform{
  
  double fov = Math.PI / 3; //60 degree
  int raysNum = 160;
  double viewDist = 320 / Math.tan((fov / 2));//distance between camera and view canvas
  //32*24 map matrix
  //0 - nothing
  //1 - world boundry
  //2 - brick wall
  //3 - stone wall
  //4 - home
  int[][] level1Map = {
	      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
	      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
	      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
	      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
	      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,1},
	      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	  } ;

  


  Tank player = new Tank();
  Color playerColor = new Color(0,255,0,150);

  @Override
  public boolean keyDown(Event e, int key) {
    switch(key){
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
    }
    return super.keyDown(e, key);
  }

  @Override
  public boolean keyUp(Event e, int key) {
    switch(key){
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
    for(int j=0;j<24;j++){
      for(int i =0;i<32;i++){
        switch(level1Map[j][i]){
        case 1:
        g.setColor(Color.DARK_GRAY);
          g.fillRect(i*20, j*20, 20, 20);
          break;
        case 2:
          g.setColor(Color.RED);
          g.fillRect(i*20, j*20, 20, 20);
          break;
        case 3:
          g.setColor(Color.WHITE);
          g.fillRect(i*20, j*20, 20, 20);
          break;
        case 4:
          g.setColor(Color.YELLOW);
          g.fillRect(i*20, j*20, 20, 20);
          break;
        }
      }
    }
    
    //player.update(level1Map);
    g.setColor(playerColor);
    g.fillRect((int)(player.x_pos*20)-10, (int)(player.y_pos*20)-10, 20, 20);
    g.setColor(Color.WHITE);
    g.drawLine((int)(player.x_pos*20), (int)(player.y_pos*20), 
        (int)((player.x_pos + Math.cos(player.angle) * 1) * 20), 
        (int)((player.y_pos + Math.sin(player.angle) * 1) * 20));
  }

  @Override
  public void update() {
  }
  
  @Override
  public void drawWorld(Graphics g) {
    player.update(level1Map);
  }
}
