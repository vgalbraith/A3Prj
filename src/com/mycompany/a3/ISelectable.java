package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface ISelectable
{
	/**
	 *  a way to mark an object as "selected" or not
	 * @param b boolean
	 */
	public abstract void setSelected(boolean b);
	
	/**
	 *  a way to test whether an object is selected
	 * @return boolean
	 */
	public abstract boolean isSelected();
	
	/** a way to determine if a pointer is "in" an object
	 * @param pPtrRelPrnt pointer position relative to the parent origin
	 * @param pCmpRelPrnt component position relative to the parent origin
	 * @return boolean
	 */
	public abstract boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	
	/**
	 *  a way to "draw" the object that knows about drawing
	 *  different ways depending on "isSelected"
	 * @param g Graphics Object
	 * @param pCmpRelPrnt component position relative to the parent origin
	 */
	public abstract void draw(Graphics g, Point pCmpRelPrnt);
}
