package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

/**
 * This class creates a Media object which loops while playing the sound.
 * */
public class BGSound implements Runnable
{
	private Media m;
	
	/**
	 * Class constructor.
	 * @param filename	String specifying filename
	 */
	public BGSound(String fileName)
	{
		while (m == null)
		{
			try
			{
				InputStream is = Display.getInstance().getResourceAsStream(getClass(),
				"/" + fileName);
				
				// attach a runnable to run when media has finished playing as the last parameter
				m = MediaManager.createMedia(is, "audio/wav", this);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Pauses (actually stops) the playback of the media file.
	 */
	public void pause()
	{
		m.pause();
	}
	
	/**
	 * Starts playing the media file.
	 */
	public void play()
	{
		m.play();
	}

	@Override
	public void run()
	{
		// start playing the sound from time zero (beginning of the sound file)
		m.setTime(0);
		m.play();
	}
}
