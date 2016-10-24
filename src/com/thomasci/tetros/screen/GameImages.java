package com.thomasci.tetros.screen;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class GameImages {
	private static final HashMap<String, Image> images = new HashMap<String, Image>();
	private static final HashMap<String, String> imagesToLoad = new HashMap<String, String>();
	
	public static Image getImage(String name) {
		return images.get(name);
	}
	
	public static synchronized void addImage(String name, String file) {
		imagesToLoad.put(name, file);
	}
	
	public static void loadImages() {
		Iterator<Entry<String, String>> it = imagesToLoad.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) it.next();
			URL url = GameImages.class.getResource("/com/thomasci/tetros/resource/" + entry.getValue());
			try {
				Image image = ImageIO.read(url);
				images.put(entry.getKey(), image);
				System.out.println("Loaded " + entry.getValue() + " as \"" + entry.getKey() + "\"");
			} catch (IOException e) {
				System.out.println("Failed to load image: " + entry.getValue());
				System.exit(0);
			}
		}
	}
}
