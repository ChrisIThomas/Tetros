package com.thomasci.tetros.screen;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSounds {
	private static final HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	private static final HashMap<String, String> soundsToLoad = new HashMap<String, String>();
	
	public static Sound getSound(String name) {
		Sound s = sounds.get(name);
		if (s == null) return Sound.NULL_SOUND;
		return sounds.get(name);
	}
	
	public static synchronized void addSound(String name, String file) {
		soundsToLoad.put(name, file);
	}
	
	public static void loadSounds() {
		Iterator<Entry<String, String>> it = soundsToLoad.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) it.next();
			URL url = GameSounds.class.getResource("/com/thomasci/tetros/resource/" + entry.getValue());
			try {
				Clip clip = AudioSystem.getClip();
				AudioInputStream in = AudioSystem.getAudioInputStream(url);
				clip.open(in);
				sounds.put(entry.getKey(), new Sound(clip));
				System.out.println("Loaded " + entry.getValue() + " as \"" + entry.getKey() + "\"");
			} catch (Exception e) {
				System.out.println("Failed to load sound: " + entry.getValue());
				e.printStackTrace();
			}
		}
	}
}
