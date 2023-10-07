package com.mycompany.a3;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection
{
	private ArrayList<GameObject> gameObjectList;
	
	public GameObjectCollection()
	{
		gameObjectList = new ArrayList<GameObject>();
	}
	
	public void add(Object ob)
	{
		gameObjectList.add((GameObject) ob);
	}

	public Iterator getIterator()
	{
		return new Iterator();
	}	
	
	private class Iterator implements IIterator
	{
		private int index;
		
		public Iterator()
		{
			this.index = -1;
		}

		public boolean hasNext()
		{
			if (gameObjectList.size() <= 0) return false;
			if (this.index == gameObjectList.size() - 1) return false;
			return true;
		}

		public GameObject getNext()
		{
			this.index++;
			return gameObjectList.get(this.index);
		}
	}
}
