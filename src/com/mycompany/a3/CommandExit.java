package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class CommandExit extends Command
{
	/**
	 * Class constructor.
	 */
	public CommandExit()
	{
		super("Exit");
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		boolean b = Dialog.show("Exit?", null, "YES", "NO");
		if (b) System.exit(0);
	}
}
