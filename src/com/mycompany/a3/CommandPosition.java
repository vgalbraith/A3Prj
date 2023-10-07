package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandPosition extends Command
{
	private MapView myMV;
	
	/**
	 * Class constructor.
	 * @param command String
	 */
	public CommandPosition(MapView mv)
	{
		super("Position");
		this.myMV = mv;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		myMV.positionChange();
	}
}
