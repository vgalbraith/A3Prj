package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandAccelerate extends Command
{
	private Game myGame;
	private GameWorld myGW;

	/**
	 * Class constructor.
	 * @param command String
	 */
	public CommandAccelerate(Game game, GameWorld gw)
	{
		super("Accelerate");
		this.myGame = game;
		this.myGW = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if (myGame.isPaused())
		{
			// Do nothing, game is paused
		}
		else
		{
			myGW.accelerate();
		}
	}
}
