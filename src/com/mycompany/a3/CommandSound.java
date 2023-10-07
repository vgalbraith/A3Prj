package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandSound extends Command
{
	private GameWorld myGW;

	/**
	 * Class constructor.
	 * @param command String
	 */
	public CommandSound(GameWorld gw)
	{
		super("Sound");
		this.myGW = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		myGW.toggleSound();
	}
}
