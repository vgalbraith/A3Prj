package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class Fixed extends GameObject implements ISelectable
{
	private boolean selected = false;
	
	/**
	 * Class constructor.
	 * 
	 * @param sz  size
	 * @param loc location
	 * @param c	  color
	 */
	public Fixed(int sz, Point loc, int c)
	{
		super(sz, loc, c);
	}
	
	public void setSelected(boolean b)
	{
		selected = b;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt)
	{
		float L = pCmpRelPrnt.getX() + this.getLocation().getX() - this.getSize()/2;
		float R = pCmpRelPrnt.getX() + this.getLocation().getX() + this.getSize()/2;
		float T = pCmpRelPrnt.getY() + this.getLocation().getY() + this.getSize()/2;
		float B = pCmpRelPrnt.getY() + this.getLocation().getY() - this.getSize()/2;
		
		float x = pPtrRelPrnt.getX();
		float y = pPtrRelPrnt.getY();
		
		if (x < L || x > R || y < B || y > T)
		{
			return false;
		}
		else return true;
	}
}
