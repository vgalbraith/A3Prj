package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;

public class CustomButton extends Button
{
	/**
	 * Class constructor.
	 */
	public CustomButton()
	{
		this.getUnselectedStyle().setBgTransparency(255);
		this.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		
		this.getDisabledStyle().setBgTransparency(255);
		this.getDisabledStyle().setBgColor(ColorUtil.WHITE);
		this.getDisabledStyle().setFgColor(ColorUtil.BLUE);
		
		this.getAllStyles().setPadding(5, 5, 3, 3);
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
	}
}
