package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandLeftTurn extends Command
{
	private GameWorld myGW;

	/**
	 * Class constructor.
	 * @param command String
	 */
	public CommandLeftTurn(GameWorld gw)
	{
		super("Left");
		this.myGW = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		myGW.turnLeft();
	}
}
