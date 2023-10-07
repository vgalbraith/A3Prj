package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class CommandHelp extends Command
{
	/**
	 * Class constructor.
	 */
	public CommandHelp()
	{
		super("Help");
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Dialog.show("Commands:",
				"'a' to accelerate\n" +
				"'b' to brake\n" + 
				"'l' to turn left\n" + 
				"'r' to turn right\n" + 
				"'f' to collide with FoodStation\n" + 
				"'g' to collide with Spider",
				"OK", null);
	}
}
