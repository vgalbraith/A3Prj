package com.mycompany.a3;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.UITimer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;

public class Game extends Form implements Runnable
{
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	
	private Command myAccelerateCommand;
	private Command myBrakeCommand;
	private Command myLeftTurnCommand;
	private Command myRightTurnCommand;
	private Command mySoundCommand;
	
	private CheckBox soundCheckBox;
	private Button accelerateButton;
	private Button brakeButton;
	private Button leftTurnButton;
	private Button rightTurnButton;
	private Button positionButton;
	private Button pauseButton;

	private UITimer timer;
	private int timerTickRate = 20;
	private boolean paused = false;
	private boolean soundPause = false;

	/**
	 * Class constructor.
	 */
	public Game()
	{
		gw = new GameWorld();
		mv = new MapView();
		sv = new ScoreView();
		gw.addObserver(mv);
		gw.addObserver(sv);
		
		// Set layout of the Form
		this.setLayout(new BorderLayout());
		
		// Create a single instance of each command object
		myAccelerateCommand = new CommandAccelerate(this, gw);
		myBrakeCommand = new CommandBrake(gw);
		myLeftTurnCommand = new CommandLeftTurn(gw);
		myRightTurnCommand = new CommandRightTurn(gw);
		Command myExitCommand = new CommandExit();
		mySoundCommand = new CommandSound(gw);
		Command myAboutCommand = new CommandAbout();
		Command myHelpCommand = new CommandHelp();
		Command myPositionCommand = new CommandPosition(mv);
		Command myPauseCommand = new CommandPause(this);
		
		// Create Toolbar
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);
		myToolbar.setTitle("FlagByFlag Game");
		
		// Create CheckBox for sound
		soundCheckBox = new CheckBox("Sound");
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		soundCheckBox.setCommand(mySoundCommand);
		
		// Add commands to side menu and title bar
		myToolbar.addCommandToSideMenu(myAccelerateCommand);
		myToolbar.addComponentToSideMenu(soundCheckBox);
		myToolbar.addCommandToSideMenu(myAboutCommand);
		myToolbar.addCommandToSideMenu(myExitCommand);
		myToolbar.addCommandToRightBar(myHelpCommand);
		
		// Bind commands to keys
		addKeyListener('a', myAccelerateCommand);
		addKeyListener('b', myBrakeCommand);
		addKeyListener('l', myLeftTurnCommand);
		addKeyListener('r', myRightTurnCommand);
		
		// Create control containers
		Container westContainer = new Container();
		Container eastContainer = new Container();
		Container southContainer = new Container();

		// Create buttons
		accelerateButton = new CustomButton();
		brakeButton = new CustomButton();
		leftTurnButton = new CustomButton();
		rightTurnButton = new CustomButton();
		positionButton = new CustomButton();
		pauseButton = new CustomButton();
		
		// Add buttons to the control containers
		westContainer.add(accelerateButton);
		westContainer.add(leftTurnButton);
		eastContainer.add(brakeButton);
		eastContainer.add(rightTurnButton);
		southContainer.add(positionButton);
		southContainer.add(pauseButton);
		
		// Add commands to the buttons
		accelerateButton.setCommand(myAccelerateCommand);
		brakeButton.setCommand(myBrakeCommand);
		leftTurnButton.setCommand(myLeftTurnCommand);
		rightTurnButton.setCommand(myRightTurnCommand);
		positionButton.setCommand(myPositionCommand);
		pauseButton.setCommand(myPauseCommand);
		
		// Add control containers to the Form
		this.add(BorderLayout.WEST, westContainer);
		this.add(BorderLayout.EAST, eastContainer);
		this.add(BorderLayout.SOUTH, southContainer);
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);
		
		// Set layout of containers
		westContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		eastContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		southContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		// Set padding of the containers
		westContainer.getAllStyles().setPadding(100, 0, 0, 0);
		eastContainer.getAllStyles().setPadding(100, 0, 0, 0);
		southContainer.getAllStyles().setPadding(0, 0, 900, 0);
		
		// disable position button
		positionButton.setEnabled(false);
		
		this.show();
		
		// code to query MapView's width and height and set them to the gw
		gw.setWidth(mv.getWidth());
		gw.setHeight(mv.getHeight());
		
		// Initialize world
		gw.init();
		
		// Create sounds
		gw.createSounds();
		revalidate();
		
		// Create a timer and schedule it to tick every 20 milliseconds
		timer = new UITimer(this);
		timer.schedule(timerTickRate, true, this);
	}

	@Override
	public void run()
	{
		// Tick clock, all Movable GameObjects move()
		gw.clockTick(timerTickRate);
		
		// check if moving caused any collisions
		IIterator iter = gw.getIterator();
		while (iter.hasNext())
		{
			// get ICollider object
			ICollider curObj = (ICollider)iter.getNext();
			
			// check if this object collides with any OTHER object
			IIterator iter2 = gw.getIterator();
			while(iter2.hasNext())
			{
				// get another ICollider object
				ICollider otherObj = (ICollider)iter2.getNext();
			
				// check for collision
				if(otherObj!=curObj)
				{
					// make sure it's not the SAME object
					if(curObj.collidesWith(otherObj))
					{
						curObj.handleCollision(otherObj);
					}
				}
			}
		}
	}

	public void pause()
	{
		if (paused)
		{
			// unpause
			paused = false;
			
			// turn sound back on if it was on before game was paused
			if (soundPause) mySoundCommand.actionPerformed(new ActionEvent(this));
			
			// enable buttons & commands
			accelerateButton.setEnabled(true);
			brakeButton.setEnabled(true);
			leftTurnButton.setEnabled(true);
			rightTurnButton.setEnabled(true);
			positionButton.setEnabled(false);
			pauseButton.setText("Pause");
			soundCheckBox.setEnabled(true);
			
			myAccelerateCommand.setEnabled(true);

			addKeyListener('a', myAccelerateCommand);
			addKeyListener('b', myBrakeCommand);
			addKeyListener('l', myLeftTurnCommand);
			addKeyListener('r', myRightTurnCommand);
			
			// schedule timer
			timer.schedule(timerTickRate, true, this);
		}
		else
		{
			// pause
			paused = true;
			
			// check if sound is on, turn it off if necessary
			soundPause = gw.getSound();
			if (soundPause) mySoundCommand.actionPerformed(new ActionEvent(this));
			
			// disable buttons & commands
			accelerateButton.setEnabled(false);
			brakeButton.setEnabled(false);
			leftTurnButton.setEnabled(false);
			rightTurnButton.setEnabled(false);
			positionButton.setEnabled(true);
			pauseButton.setText("Play");
			soundCheckBox.setEnabled(false);
			
			myAccelerateCommand.setEnabled(false); // this didn't work, so I added a conditional in my accelerate command

			removeKeyListener('a', myAccelerateCommand);
			removeKeyListener('b', myBrakeCommand);
			removeKeyListener('l', myLeftTurnCommand);
			removeKeyListener('r', myRightTurnCommand);
			
			// cancel timer
			timer.cancel();
		}
	}
	
	public boolean isPaused()
	{
		return paused;
	}
}
