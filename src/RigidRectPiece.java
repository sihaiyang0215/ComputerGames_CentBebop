

import java.awt.Graphics;
import java.awt.Image;

// A RECTANGLE SHAPED THING

public class RigidRectPiece extends RectPiece
{

	public int width;
	public int height;

	public Piece setBounds(double x, double y, double width, double height) {
	
		this.width = (int)width;
		this.height = (int)height;

		return super.setBounds(x, y, width, height);
	}

	boolean mouseDrag(int x, int y) {
		
		//do nothing because they are rigid 
		return false;
	}


}

