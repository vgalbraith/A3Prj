package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandBrake extends Command
{
	private GameWorld myGW;

	/**
	 * Class constructor.
	 * @param command String
	 */
	public CommandBrake(GameWorld gw)
	{
		super("Brake");
		this.myGW = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		myGW.brake();
	}
}
