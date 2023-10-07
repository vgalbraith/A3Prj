package com.mycompany.a3;

import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Ant extends Movable implements ISteerable, ICollider
{	
	private GameWorld myGW;
	private ArrayList<ICollider> collidingList = new ArrayList<ICollider>();
	
	private static Ant theAnt;
	private int maximumSpeed;
	private int foodLevel;
	private int foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached;

	/**
	 * Class constructor.
	 * 
	 * @param loc location
	 */
	private Ant(Point loc, GameWorld gw)
	{
		super(100,						// size:	 			100
			  loc,						// location: 			loc
			  ColorUtil.rgb(255, 0, 0),	// color: 	 			red
			  0,						// heading:	 			0
			  50,						// speed:	 			50
			  gw);
		this.maximumSpeed = 150;		// maximumSpeed: 		150
		this.foodLevel = 5000;			// foodLevel: 			5000
		this.foodConsumptionRate = 2;	// foodConsumptionRate: 2
		this.healthLevel = 10;			// healthLevel: 		10
		this.lastFlagReached = 1;		// lastFlagReached: 	1
		
		myGW = gw;
	}
	
	public static Ant getAnt(Point loc, GameWorld gw)
	{
		if (theAnt == null) theAnt = new Ant(loc, gw);
		return theAnt;
	}
	
	public void resetAnt(Point loc)
	{
		super.setLocation(loc);
		super.setColor(ColorUtil.rgb(255, 0, 0));
		super.setHeading(0);
		super.setSpeed(30);
		this.foodLevel = 5000;
		this.healthLevel = 10;
		this.lastFlagReached = 1;
	}

	// heading methods
	public void steerHeading(int h)
	{
		// Change heading by +h
		super.setHeading(getHeading() + h);
	}

	// speed methods
	public void changeSpeed(int sp)
	{
		// Change speed by +sp
		super.setSpeed(getSpeed() + sp);
		
		// Enforce speed-limitation rule
		if (getSpeed() < 0) super.setSpeed(0);
		if (getSpeed() > (maximumSpeed * healthLevel) / 10)
		{
			super.setSpeed((maximumSpeed * healthLevel) / 10);
		}
	}

	// foodLevel methods
	public int getFoodLevel()
	{
		return foodLevel;
	}
	public void changeFoodLevel(int f)
	{
		// Change foodLevel by +f
		this.foodLevel += f;
		
		if (foodLevel <= 0)
		{
			this.foodLevel = 0;
			super.setSpeed(0);
		}
	}
	
	// foodConsumptionRate methods
	public int getFoodConsumptionRate()
	{
		return foodConsumptionRate;
	}

	// healthLevel methods
	public int getHealthLevel()
	{
		return healthLevel;
	}
	public void changeHealthLevel(int hp)
	{
		// Change healthLevel by +hp
		this.healthLevel += hp;
		
		// Enforce speed-limitation rule
		if (getSpeed() > (maximumSpeed * healthLevel) / 10)
		{
			super.setSpeed((maximumSpeed * healthLevel) / 10);
		}
	}

	// lastFlagReached methods
	public int getLastFlagReached()
	{
		return lastFlagReached;
	}
	public void setLastFlagReached(int sNum)
	{
		this.lastFlagReached = sNum;
	}

	@Override
	public String toString()
	{
		String s = "Ant: loc="	  		   + Math.round(getLocation().getX()*10.0)/10.0 + ","
										   + Math.round(getLocation().getY()*10.0)/10.0 +
				   " color=["   		   + ColorUtil.red(getColor()) + ","
				 			      		   + ColorUtil.green(getColor()) + ","
				 			      		   + ColorUtil.blue(getColor()) + "]" +
	 			   " heading="  		   + getHeading() +
	 			   " speed="    		   + getSpeed() +
				   " size="     		   + getSize() +
		   		   " maxSpeed=" 		   + maximumSpeed +
		   		   " foodConsumptionRate=" + foodConsumptionRate + "\n";
		return s;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		int x = (int)(pCmpRelPrnt.getX() + getLocation().getX());
		int y = (int)(pCmpRelPrnt.getY() + getLocation().getY());
		int s = getSize();
		
		g.setColor(getColor());
		g.fillArc(x - s/2, // X-coord (upper-left corner of bounding box)
				  y - s/2, // Y-coord (upper-left corner of bounding box)
				  s,	   // width
				  s,	   // height
				  0,	   // angle start
				  360);	   // angle end
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
			if (otherObject instanceof Spider) myGW.hitSpider();
			if (otherObject instanceof Flag) myGW.hitFlag((Flag)otherObject);
			if (otherObject instanceof FoodStation) myGW.hitFoodStation(otherObject);
	
			collidingList.add(otherObject);
		}
	}
}
