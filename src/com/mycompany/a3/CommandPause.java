package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandPause extends Command
{
	private Game myGame;
	
	/**
	 * Class constructor.
	 * @param command String
	 */
	public CommandPause(Game g)
	{
		super("Pause");
		this.myGame = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		myGame.pause();
	}
}
