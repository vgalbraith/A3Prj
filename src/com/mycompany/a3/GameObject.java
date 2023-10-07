package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class GameObject implements IDrawable
{
	// each type of game object has a different shape which can be bounded by a square.
	// The size attribute provides the length of this bounding square.
	private int size;
	private Point location;
	private int color;

	/**
	 * Class constructor.
	 * 
	 * @param sz  size
	 * @param loc location
	 * @param c	  color
	 */
	public GameObject(int sz, Point loc, int c)
	{
		this.size = sz;
		this.location = loc;
		this.color = c;
	}
	
	// size methods
	public int getSize()
	{
		return size;
	}
	
	// location methods
	public Point getLocation()
	{
		return location;
	}
	public void setLocation(Point loc)
	{
		this.location = loc;
	}
	
	// color methods
	public int getColor()
	{
		return color;
	}
	public void setColor(int c)
	{
		this.color = c;
	}
}
