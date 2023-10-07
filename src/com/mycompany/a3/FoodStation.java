package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

import java.util.ArrayList;
import java.util.Random;

public class FoodStation extends Fixed implements ICollider
{	
	private GameWorld myGW;
	private ArrayList<ICollider> collidingList = new ArrayList<ICollider>();
	
	private int capacity;

	/**
	 * Class constructor.
	 * 
	 * Randomly assigns size, location
	 */
	public FoodStation(GameWorld gw)
	{
		// Instantiate variables
		super(100 + new Random().nextInt(40),							   // size:	 100-140
			  new Point((int)(gw.getWidth()  * new Random().nextFloat()),  // x-coord:	 0-maxWidth
						(int)(gw.getHeight() * new Random().nextFloat())), // y-coord:	 0-maxHeight
			  ColorUtil.rgb(0, 128, 0));								   // color:	 green  
		this.capacity = getSize();										   // capacity: 1:1 ratio to size
	
		myGW = gw;
	}

	// capacity methods
	public int getCapacity()
	{
		return capacity;
	}
	public void setCapacity(int f)
	{
		this.capacity = f;
	}
	
	@Override
	public String toString()
	{
		String s = "FoodStation: loc=" + Math.round(getLocation().getX()*10.0)/10.0 + ","
				   					   + Math.round(getLocation().getY()*10.0)/10.0 +
				   " color=[" 		   + ColorUtil.red(getColor())+ ","
	 			    				   + ColorUtil.green(getColor())+ ","
	 			    				   + ColorUtil.blue(getColor()) + "]" +
				   " size=" 		   + getSize() +
				   " capacity=" 	   + capacity + "\n";
		return s ;
	}

	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		int x = (int)(pCmpRelPrnt.getX() + getLocation().getX());
		int y = (int)(pCmpRelPrnt.getY() + getLocation().getY());
		int s = getSize();
		
		// Draw FoodStation
		g.setColor(getColor());
		if (isSelected())
		{
			g.drawRect(x - s/2,	// X-coord
					   y - s/2, // Y-coord
					   s,		// width
					   s);		// height
		}
		else
		{
			g.fillRect(x - s/2,	// X-coord
					   y - s/2, // Y-coord
					   s,		// width
					   s);		// height
		}
		
		// Draw capacity
		g.setColor(ColorUtil.BLACK);
		g.drawString("" + capacity, x - s/4, y - s/4);
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
//			if (otherObject instanceof Ant) myGW.hitFoodStation(this); // handled in Ant
		}
	}
}
