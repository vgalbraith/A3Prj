package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

/** 
 * This class encapsulates a sound file as an Media inside a
 * "Sound" object, and provides a method for playing the Sound.
 */
public class Sound
{
	private Media m;
	
	/**
	 * Class constructor.
	 * @param filename	String specifying filename
	 */
	public Sound(String fileName)
	{
		while (m == null)
		{
			try
			{
				InputStream is = Display.getInstance().getResourceAsStream(getClass(),
				"/" + fileName);
				m = MediaManager.createMedia(is, "audio/wav");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Starts playing the media file.
	 */
	public void play()
	{
		// start playing the sound from time zero (beginning of the sound file)
		m.setTime(0);
		m.play();
	}
}
