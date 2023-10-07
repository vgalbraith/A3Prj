package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer
{
	private GameWorld myGW;
	private boolean positionChange = false;
	
	/**
	 * Class constructor.
	 */
	public MapView()
	{
		// empty container
		// use setBorder() method of Style class to give it a red border
		getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.rgb(255, 0, 0)));
	}
	
	@Override
	public void update(Observable o, Object data)
	{
		this.myGW = (GameWorld)o;
		
		// code here to call the method in GameWorld (Observable) that output the
		// game object information to the console
		myGW.map();
		
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		IIterator theElements = myGW.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof IDrawable)
			{
				ob.draw(g, pCmpRelPrnt);
			}
		}
	}
	
	@Override
	public void pointerPressed(int x, int y)
	{
		//make pointer location relative to parent’s origin
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		if (positionChange)
		{
			IIterator theElements = myGW.getIterator();
			while (theElements.hasNext())
			{
				GameObject ob = (GameObject)theElements.getNext();
				if (ob instanceof ISelectable)
				{
					if (((ISelectable)ob).isSelected())
					{
						// change location of ISelectable to pointer position
						ob.setLocation(new Point(pPtrRelPrnt.getX() - getX(),
												 pPtrRelPrnt.getY() - getY()));
					}
					else
					{
						// do nothing for unselected objects
					}
				}
			}
			
			positionChange = false;
		}
		else
		{
			IIterator theElements = myGW.getIterator();
			while (theElements.hasNext())
			{
				GameObject ob = (GameObject)theElements.getNext();
				if (ob instanceof ISelectable)
				{
					if (((ISelectable)ob).contains(pPtrRelPrnt, pCmpRelPrnt))
					{
						((ISelectable)ob).setSelected(true);
					}
					else
					{
						((ISelectable)ob).setSelected(false);
					}
				}
			}
		}
		
		repaint();
	}
	
	public void positionChange()
	{
		positionChange = true;
	}
}
