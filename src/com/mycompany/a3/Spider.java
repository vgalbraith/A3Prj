package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

import java.util.ArrayList;
import java.util.Random;

public class Spider extends Movable implements ICollider
{
	private GameWorld myGW;
	private ArrayList<ICollider> collidingList = new ArrayList<ICollider>();
	
	/**
	 * Class constructor.
	 * 
	 * Randomly assigns size, location, heading, and speed
	 */
	public Spider(GameWorld gw)
	{
		super(new Random().nextInt(40) + 100, 					 			 // size:	 100-140
			  new Point((int)(gw.getWidth() * new Random().nextFloat()),	 // x-coord: 0-1000
						(int)(gw.getHeight() * new Random().nextFloat())),   // y-coord: 0-1000
			  ColorUtil.BLACK,									 			 // color:	 black
			  new Random().nextInt(359),						 			 // heading: 0-359
			  new Random().nextInt(50) + 50,						 		 // speed:	 50-100
			  gw);
		myGW = gw;
	}

	@Override
	// move methods
	public void move(int tickRate)
	{
		super.move(tickRate);
		
		int s = getSize();
		if (getLocation().getX() == s/2 || getLocation().getX() == myGW.getWidth() - s/2 ||
			getLocation().getY() == s/2 || getLocation().getY() == myGW.getHeight() - s/2)
		{
			// Spider collided with wall, set new random heading
			setHeading(new Random().nextInt(359));
		}
	}

	// heading methods
	public void updateHeading()
	{
		// Update heading by adding or subtracting up to 5 degrees
		setHeading(getHeading() + new Random().nextInt(10) - 5);
	}
	
	// color methods
	@Override
	public void setColor(int c)
	{
		// Spiders are not allowed to change color once they are created
	}
	
	@Override
	public String toString()
	{
		String s = "Spider: loc=" + Math.round(getLocation().getX()*10.0)/10.0 + ","
				   				  + Math.round(getLocation().getY()*10.0)/10.0 +
				   " color=["     + ColorUtil.red(getColor()) + ","
				 			      + ColorUtil.green(getColor()) + ","
				 			      + ColorUtil.blue(getColor()) + "]" +
	 			   " heading="    + getHeading() +
	 			   " speed="      + getSpeed() +
				   " size="       + getSize() + "\n";
		return s;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		int x = (int)(pCmpRelPrnt.getX() + getLocation().getX());
		int y = (int)(pCmpRelPrnt.getY() + getLocation().getY());
		int s = getSize();
		int xPoints[] = {x, x - s/2, x + s/2};
		int yPoints[] = {y + s/2, y - s/2, y - s/2};
		
		// Draw Spider
		g.setColor(getColor());
		g.drawPolygon(xPoints, yPoints, 3);
	}

	@Override
	public boolean collidesWith(ICollider otherObject)
	{
		float L1 = this.getLocation().getX() - this.getSize()/2;
		float R1 = this.getLocation().getX() + this.getSize()/2;
		float T1 = this.getLocation().getY() + this.getSize()/2;
		float B1 = this.getLocation().getY() - this.getSize()/2;
		
		GameObject that = (GameObject)otherObject;
		float L2 = that.getLocation().getX() - that.getSize()/2;
		float R2 = that.getLocation().getX() + that.getSize()/2;
		float T2 = that.getLocation().getY() + that.getSize()/2;
		float B2 = that.getLocation().getY() - that.getSize()/2;
		
		if (R1 < L2 || L1 > R2 || T2 < B1 || T1 < B2)
		{
			// no collision detected
			if (collidingList.contains(otherObject)) collidingList.remove(otherObject);
			return false;
		}
		else
		{
			// collision detected
			return true;
		}
	}

	@Override
	public void handleCollision(ICollider otherObject)
	{
		if (collidingList.contains(otherObject))
		{
			// do nothing, already handled previously
		}
		else
		{
			collidingList.add(otherObject);
//			if (otherObject instanceof Ant) myGW.hitSpider(); // handled in Ant
		}
	}
}
