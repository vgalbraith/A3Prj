package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
//import com.codename1.ui.Command;
//import com.codename1.ui.Dialog;
//import com.codename1.ui.TextField;

import java.util.Observable;

// For now, assume that FlagByFlag game world size is fixed and
// covers 1000 (width) x 1000 (height) area.
// The origin of the “world” (location (0,0)) is the lower left hand corner.
public class GameWorld extends Observable
{
	private int clock;
	private int lives;
	private int totalFlags;
	private boolean sound;
	private GameObjectCollection theCollection;
	private float width;
	private float height;
	private Point antStart = new Point(200, 200);
	private Sound cheer, squeak, chomp;
	private BGSound forest;

	/**
	 * Class constructor.
	 */
	public GameWorld()
	{
		// Instantiate variables
		theCollection = new GameObjectCollection();
		this.clock = 0;
		this.lives = 3;
		this.sound = false;
	}
	
	public void init()
	{
		if (lives == 0)
		{
			// No more lives, game is lost
			System.out.println("Game over, you failed!");
			System.exit(0);
		}
		
		// Reset sequence number of flags to start at 1
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Flag)
			{
				((Flag)ob).resetSequenceNumber();				
				break;
			}
		}
		
		// Start with empty collection
		theCollection = new GameObjectCollection();

		// Add at least 2 FoodStations
		int totalFoodStations = 3;
		for (int i=0; i<totalFoodStations; i++) theCollection.add(new FoodStation(this));
		
		// Add 4-9 Flags to theCollection, the first flag starts at the location of the Ant.
		totalFlags = 4;
		for (int i=0; i<totalFlags; i++)
		{
			if (i == 0) theCollection.add(new Flag(antStart, this));
			if (i == 1) theCollection.add(new Flag(new Point(200, 800), this));
			if (i == 2) theCollection.add(new Flag(new Point(700, 800), this));
			if (i == 3) theCollection.add(new Flag(new Point(900, 400), this));
			if (i == 4) theCollection.add(new Flag(new Point(1300, 1100), this));
			if (i == 5) theCollection.add(new Flag(new Point(500, 200), this));
			if (i == 6) theCollection.add(new Flag(new Point(300, 1100), this));
			if (i == 7) theCollection.add(new Flag(new Point(400, 500), this));
			if (i == 8) theCollection.add(new Flag(new Point(1200, 600), this));
			if (i == 9) theCollection.add(new Flag(new Point(400, 1100), this));
		}
		
		// Add at least 2 Spiders
		int totalSpiders = 3;
		for (int i=0; i<totalSpiders; i++) theCollection.add(new Spider(this));
		
		// Add 1 Ant to theCollection
		theCollection.add(Ant.getAnt(antStart, this));
		
		setChanged();
		notifyObservers();
	}
	
	public void accelerate()
	{
		// Increase the Ant's speed by 5
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				((Ant)ob).changeSpeed(5);
				System.out.println("+5 speed (accelerated)");
				
				setChanged();
				notifyObservers();
			}
		}
	}

	public void brake()
	{
		// Deccrease the Ant's speed by 5
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				((Ant)ob).changeSpeed(-5);
				System.out.println("-5 speed (applied brakes)");
				
				setChanged();
				notifyObservers();
			}
		}
	}

	public void turnLeft()
	{
		// Adjust the Ant's heading by -5 degrees
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				((Ant)ob).steerHeading(-5);
				System.out.println("-5 heading (turned left)");
				
				setChanged();
				notifyObservers();
			}
		}
	}

	public void turnRight()
	{
		// Adjust the Ant's heading by +5 degrees
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				((Ant)ob).steerHeading(5);
				System.out.println("+5 heading (turned right)");
				
				setChanged();
				notifyObservers();
			}
		}
	}

	// the ant collided with a Flag
	public void hitFlag(Flag f)
	{
		// Check which Flag the Ant last hit
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				int lastFlag = ((Ant)ob).getLastFlagReached();
				
				int sNum = f.getSequenceNumber();
				if (sNum == lastFlag + 1)
				{
					System.out.println("Hit flag " + sNum);
					if (sound) cheer.play();
					
					// update lastFlagReached for the Ant
					((Ant)ob).setLastFlagReached(sNum);

					// There are no more Flags, game is won
					if (sNum == totalFlags)
					{
						System.out.println("Game over, you win! Total time: " + clock);
						System.exit(0);
					}
				}
			}
		}

		setChanged();
		notifyObservers();
	}
	
	// the ant collided with a FoodStation
	public void hitFoodStation(ICollider otherObj)
	{
		int cap = 0;

		// Find a FoodStation
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob == otherObj && ((FoodStation)ob).getCapacity() > 0)
			{
				// Reduce FoodStation's capacity to 0
				cap = ((FoodStation)ob).getCapacity();
				((FoodStation)ob).setCapacity(0);
				
				// Fade the color of the FoodStation to light green
				((FoodStation)ob).setColor(ColorUtil.rgb(140, 239, 117));
				
				// Add a new FoodStation
				theCollection.add(new FoodStation(this));
				
				// Find the Ant and update its FoodLevel
				IIterator theElements2 = theCollection.getIterator();
				while (theElements2.hasNext())
				{
					GameObject ob2 = (GameObject)theElements2.getNext();
					if (ob2 instanceof Ant )
					{
						// Increase Ant's foodLevel by the hit FoodStation's capacity
						((Ant)ob2).changeFoodLevel(cap);
						System.out.println("+" + cap + " food (hit FoodStation)");
						if (sound) chomp.play();
						
						setChanged();
						notifyObservers();
					}
				}
				
				break; // No need to find another FoodStation
			}
		}
		
		
	}

	// the ant collided with a Spider
	public void hitSpider()
	{
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				// Reduce Ant's health by 1
				((Ant)ob).changeHealthLevel(-1);
				System.out.println("-1 health (hit spider)");
				if (sound) squeak.play();
				
				// Fade Ant color
				int green = ColorUtil.green(((Ant)ob).getColor());
				int blue = ColorUtil.blue(((Ant)ob).getColor());
				((Ant)ob).setColor(ColorUtil.rgb(255, green + 25, blue + 25));
				
				if (((Ant)ob).getHealthLevel() == 0)
				{
					// Ant's health is 0, life lost
					this.lives--;
					System.out.println("-1 life (0 health)");
					((Ant)ob).resetAnt(antStart);
					this.init();
				}
			}
		}
		
		setChanged();
		notifyObservers();
	}

	public void clockTick(int tickRate)
	{
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			// Spiders update their heading
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Spider)
			{
				((Spider)ob).updateHeading();
			}
			
			// Movables move
			if (ob instanceof Movable)
			{
				((Movable)ob).move(tickRate);
			}
			
			// Reduce Ant's foodLevel by foodConsumptionRate
			if (ob instanceof Ant)
			{
				((Ant)ob).changeFoodLevel(-((Ant)ob).getFoodConsumptionRate());
				
				if (((Ant)ob).getFoodLevel() == 0)
				{
					// Ant's food is 0, life lost
					this.lives--;
					System.out.println("-1 life (0 food)");
					((Ant)ob).resetAnt(antStart);
					this.init();
				}
			}
		}

		// Increment game clock
		this.clock++;
		System.out.println("+1 time (clock ticked)");
		
		setChanged();
		notifyObservers();
	}

	public void map()
	{
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			System.out.print(((GameObject)theElements.getNext()).toString());
		}
	}

	public float getHeight()
	{
		return this.height;
	}
	
	public void setHeight(float f)
	{
		this.height = f;
	}

	public float getWidth()
	{
		return this.width;
	}
	
	public void setWidth(float f)
	{
		this.width = f;
	}

	public int getClock()
	{
		return clock;
	}

	public int getLives()
	{
		return lives;
	}

	public int getLastFlagReached()
	{
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				return ((Ant)ob).getLastFlagReached();
			}
		}
		return -1;
	}

	public int getFoodLevel()
	{
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				return ((Ant)ob).getFoodLevel();
			}
		}
		return -1;
	}

	public int getHealthLevel()
	{
		IIterator theElements = theCollection.getIterator();
		while (theElements.hasNext())
		{
			GameObject ob = (GameObject)theElements.getNext();
			if (ob instanceof Ant)
			{
				return ((Ant)ob).getHealthLevel();
			}
		}
		return -1;
	}

	public boolean getSound()
	{
		return sound;
	}

	public void toggleSound()
	{
		if (sound)
		{
			sound = false;
			forest.pause();
		}
		else
		{
			sound = true;
			forest.run();
		}

		setChanged();
		notifyObservers();
	}
	
	public IIterator getIterator()
	{
		return theCollection.getIterator();
	}

	/**
	 * Instantiate a single sound object for each audio file
	 * (including the background audio).
	 */
	public void createSounds()
	{
		// Create collisions sounds
		cheer = new Sound("5_jubelndekids.wav");
		squeak = new Sound("Fairy-squirm-01.wav");
		chomp = new Sound("TOON50.wav");
		
		// Create background music
		forest = new BGSound("forest1.wav");
	}
}
