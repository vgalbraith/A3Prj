package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;

public class ScoreView extends Container implements Observer
{
	private Label timeValueLabel = new Label();
	private Label livesValueLabel = new Label();
	private Label flagValueLabel = new Label();
	private Label foodValueLabel = new Label();
	private Label healthValueLabel = new Label();
	private Label soundValueLabel = new Label();
	
	/**
	 * Class constructor.
	 */
	public ScoreView()
	{
		// Add appropriate labels using CN1 built-in Label class
		Label timeLabel = new Label("Time:");
		Label livesLabel = new Label("Lives Left:");
		Label flagLabel = new Label("Last Flag Reached:");
		Label foodLabel = new Label("Food Level:");
		Label healthLabel = new Label("Health Level:");
		Label soundLabel = new Label("Sound:");

		this.addComponent(timeLabel);
		this.addComponent(timeValueLabel);
		this.addComponent(livesLabel);
		this.addComponent(livesValueLabel);
		this.addComponent(flagLabel);
		this.addComponent(flagValueLabel);
		this.addComponent(foodLabel);
		this.addComponent(foodValueLabel);
		this.addComponent(healthLabel);
		this.addComponent(healthValueLabel);
		this.addComponent(soundLabel);
		this.addComponent(soundValueLabel);

		this.getAllStyles().setPadding(0, 0, 350, 0);
	}
	
	@Override
	public void update(Observable o, Object data)
	{
		// update contents of labels using Label method setText()
		// if no change, try calling revalidate()
		timeValueLabel.setText("" + ((GameWorld)o).getClock());
		livesValueLabel.setText("" + ((GameWorld)o).getLives());
		flagValueLabel.setText("" + ((GameWorld)o).getLastFlagReached());
		foodValueLabel.setText("" + ((GameWorld)o).getFoodLevel());
		healthValueLabel.setText("" + ((GameWorld)o).getHealthLevel());
		String s = "OFF";
		if (((GameWorld)o).getSound()) s = "ON";
		soundValueLabel.setText(s);

		timeValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		livesValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		flagValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		foodValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		healthValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		soundValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		
		revalidate();
	}
}
