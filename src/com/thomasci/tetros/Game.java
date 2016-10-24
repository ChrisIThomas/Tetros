package com.thomasci.tetros;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;
import com.thomasci.tetros.screen.GameSounds;
import com.thomasci.tetros.shop.ShopItem;
import com.thomasci.tetros.state.GameState;
import com.thomasci.tetros.state.StateWorld;

public class Game {
	private static Game instance;
	private boolean running;
	private int fps, idealFPS = 17;
	private static boolean debugging = false;
	private GameState currentState, nextState;
	private StateWorld worldState;
	
	public Game() {
		instance = this;
		new GameScreen(800, 600);
	}
	
	public void initialize() {
		GameImages.addImage("tiles", "tiles.png");
		GameImages.addImage("ents", "ents.png");
		GameImages.addImage("items", "items.png");
		GameImages.addImage("clouds", "clouds.png");
		GameImages.addImage("gui", "gui.png");
		GameImages.addImage("particle", "particle.png");
		GameImages.addImage("stateBuy", "stateBuy.png");
		GameImages.addImage("cursor", "cursor.png");
		GameImages.loadImages();
		
		GameSounds.addSound("hit", "hit.wav");
		GameSounds.addSound("dig", "dig.wav");
		GameSounds.addSound("btn", "btn.wav");
		GameSounds.addSound("place", "place.wav");
		GameSounds.addSound("coin", "coin.wav");
		GameSounds.addSound("step", "step.wav");
		GameSounds.addSound("jump", "jump.wav");
		GameSounds.addSound("chicken", "chicken.wav");
		GameSounds.addSound("zombie", "zombie.wav");
		GameSounds.addSound("caveman", "caveman.wav");
		GameSounds.addSound("minotaur", "minotaur.wav");
		GameSounds.loadSounds();
		
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(GameImages.getImage("cursor"), new Point(6, 6), "game cursor");
		GameScreen.instance().getJFrame().getContentPane().setCursor(cursor);
		
		ShopItem.prepareItems();
		
		worldState = new StateWorld();
		swapState(worldState);
		
		System.out.println("Initialized");
	}
	
	public void finalize() {
		currentState.finalize();
	}
	
	public void run() {
		running = true;
		long time = System.currentTimeMillis();
		long drawTime = System.currentTimeMillis();
		long sec = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			if (System.currentTimeMillis() - sec >= 1000) {
				sec += 1000;
				fps = frames;
				frames = 0;
			}
			if (nextState != null) {
				if (currentState != null) {
					currentState.finalize();
					if (currentState.pauseGame()) worldState.unpause();
				}
				nextState.initialize();
				currentState = nextState;
				nextState = null;
				if (currentState.pauseGame()) worldState.pause();
			}
			if (System.currentTimeMillis() - time >= idealFPS) {
				time += idealFPS;
				GameScreen.instance().updateInput();
				tick(idealFPS / 1000.0f);
				frames++;
			}
			if (System.currentTimeMillis() - drawTime >= 33) {
				drawTime += 33;
				draw(drawTime);
			}
		}
	}
	
	private void tick(float dt) {
		currentState.tick(dt);
		if (currentState != worldState) {
			worldState.tick(dt);
		}
	}
	
	private void draw(long time) {
		Globals.renderCount = 0;
		GameScreen screen = GameScreen.instance();
		screen.getImage().clear(68, 165, 255);
		if (currentState.drawWorld()) {
			worldState.draw(time);
			screen.getImage().shade(0.5f);
		}
		currentState.draw(time);
		if (debugging) {
			screen.getImage().drawText(fps + " FPS", 0, 12, Color.WHITE);
			screen.getImage().drawText(
					Globals.renderCount + "/" + worldState.getWorld().getEntities().size() +
					" Visible Ents", 0, 24, Color.WHITE
			);
		}
		screen.getJFrame().repaint();
	}
	
	public void keyPress(int key) {
		if (key == 112) { //F1
			debugging = !debugging;
		} else if (key == 122) { //F11
			GameScreen.instance().toggleFullscreen();
		}
		if (currentState != null) currentState.keyPress(key);
	}
	
	public void keyDown(int key, long time) {
		if (currentState != null) currentState.keyDown(key, time);
	}
	
	public void keyRelease(int key) {
		if (currentState != null) currentState.keyRelease(key);
	}
	
	public void mousePress(int btn, int x, int y) {
		if (currentState != null) currentState.mousePress(btn, x, y);
	}
	
	public void mouseRelease(int btn, int x, int y) {
		if (currentState != null) currentState.mouseRelease(btn, x, y);
	}
	
	public GameState getCurrentState() {
		return currentState;
	}
	
	public StateWorld getWorldState() {
		return worldState;
	}
	
	public void swapState(GameState state) {
		nextState = state;
	}
	
	public void swapToWorldState() {
		nextState = worldState;
	}
	
	public int getFPS() {
		return fps;
	}
	
	public synchronized void end() {
		running = false;
	}
	
	public static Game instance() {
		return instance;
	}
	
	public static boolean isDebugging() {
		return debugging;
	}
}
