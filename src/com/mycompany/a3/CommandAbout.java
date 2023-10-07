package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class CommandAbout extends Command
{
	/**
	 * Class constructor.
	 */
	public CommandAbout()
	{
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Dialog.show("About", "Victor Galbraith - CSC-133", "OK", null);
	}
}
