package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class Movable extends GameObject
{
	private GameWorld myGW;
	
	private int heading;
	private int speed;

	/**
	 * Class constructor.
	 * 
	 * @param sz  size
	 * @param loc location
	 * @param c	  color
	 * @param h	  heading
	 * @param sp  speed
	 */
	public Movable(int sz, Point loc, int c, int h, int sp, GameWorld gw)
	{
		super(sz, loc, c);
		this.heading = h;
		this.speed = sp;
		myGW = gw;
	}

	public void move(int tickRate)
	{
		// Update location based on current heading and speed
		this.setLocation(new Point(
				getLocation().getX() + (float)Math.cos(Math.toRadians(90 - heading)) * speed * tickRate/1000,
				getLocation().getY() + (float)Math.sin(Math.toRadians(90 - heading)) * speed * tickRate/1000));
		
		// Constrict Movable object to the defined game grid
		if (getLocation().getX() < getSize()/2)
		{
			setLocation(new Point(getSize()/2, getLocation().getY()));
		}
		else if (getLocation().getX() > myGW.getWidth() - getSize()/2)
		{
			setLocation(new Point(myGW.getWidth() - getSize()/2, getLocation().getY()));
		}
		if (getLocation().getY() < getSize()/2)
		{
			setLocation(new Point(getLocation().getX(), getSize()/2));
		}
		else if (getLocation().getY() > myGW.getHeight() - getSize()/2)
		{
			setLocation(new Point(getLocation().getX(), myGW.getHeight() - getSize()/2));
		}
	}

	// heading methods
	public int getHeading()
	{
		return heading;
	}
	public void setHeading(int h)
	{
		this.heading = h;
	}

	// speed methods
	public int getSpeed()
	{
		return speed;
	}
	public void setSpeed(int sp)
	{
		this.speed = sp;
	}
}
