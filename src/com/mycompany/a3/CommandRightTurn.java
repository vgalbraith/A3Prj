package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandRightTurn extends Command
{
	private GameWorld myGW;

	/**
	 * Class constructor.
	 * @param command String
	 */
	public CommandRightTurn(GameWorld gw)
	{
		super("Right");
		this.myGW = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		myGW.turnRight();
	}
}
