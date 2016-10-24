package com.thomasci.tetros.screen;

import javax.sound.sampled.Clip;

public class Sound {
	private final Clip clip;
	
	public Sound(Clip clip) {
		this.clip = clip;
	}
	
	public void loop() {
		clip.setLoopPoints(0, -1);
		new Thread() {
			public void run() {
				synchronized (clip) {
					clip.stop();
					clip.setFramePosition(0);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				};
			}
		}.start();
	}
	
	public void play() {
		new Thread() {
			public void run() {
				synchronized (clip) {
					clip.stop();
					clip.setFramePosition(0);
					clip.start();
				};
			}
		}.start();
	}
	
	public static final Sound NULL_SOUND = new Sound(null) {
		@Override
		public void loop() {}
		
		@Override
		public void play() {}
	};
}
