package com.mycompany.a3;

import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Flag extends Fixed implements ICollider
{
	private GameWorld myGW;
	private ArrayList<ICollider> collidingList = new ArrayList<ICollider>();
	
	private int sequenceNumber = 1;
	private static int nextSequenceNumber = 1;

	/**
	 * Class constructor.
	 * 
	 * @param loc location
	 */
	public Flag(Point loc, GameWorld gw)
	{
		super(100,								  // size:		   	 100
			  loc,								  // location:	   	 loc
			  ColorUtil.rgb(0, 128, 255));		  // color:		  	 blue
		this.sequenceNumber = nextSequenceNumber; // sequenceNumber: previous Flag + 1
		nextSequenceNumber++;
		
		myGW = gw;
	}
	
	public void resetSequenceNumber()
	{
		nextSequenceNumber = 1;
	}
	
	// color methods
	@Override
	public void setColor(int c)
	{
		// Flags are not allowed to change color once they are created
	}
	
	@Override
	public String toString()
	{
		String s = "Flag: loc=" + Math.round(getLocation().getX()*10.0)/10.0 + ","
				   				+ Math.round(getLocation().getY()*10.0)/10.0 +
				   " color=[" 	+ ColorUtil.red(getColor())+ ","
			 	 			    + ColorUtil.green(getColor())+ ","
				 			    + ColorUtil.blue(getColor()) + "]" +
				   " size="  	+ getSize() +
				   " seqNum=" 	+ this.sequenceNumber + "\n";
		return s ;
	}

	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		int x = (int)(pCmpRelPrnt.getX() + getLocation().getX());
		int y = (int)(pCmpRelPrnt.getY() + getLocation().getY());
		int s = getSize();
		int xPoints[] = {x, x - s/2, x + s/2};
		int yPoints[] = {y + s/2, y - s/2, y - s/2};
		
		// Draw Flag
		g.setColor(getColor());
		if (isSelected())
		{
			g.drawPolygon(xPoints, yPoints, 3);
		}
		else
		{
			g.fillPolygon(xPoints, yPoints, 3);
		}
		
		// Draw sequenceNumber
		g.setColor(ColorUtil.BLACK);
		g.drawString("" + this.sequenceNumber, x - s/8, y - s/4);
	}

	public int getSequenceNumber()
	{
		return this.sequenceNumber;
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
//			if (otherObject instanceof Ant) myGW.hitFlag(this); // handled in Ant
		}
	}
}
