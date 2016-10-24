package com.thomasci.tetros.state;

public abstract class GameState {
	public abstract void initialize();
	public abstract void finalize();
	public abstract void tick(float dt);
	public abstract void draw(long time);
	public abstract void keyPress(int key);
	public abstract void keyDown(int key, long time);
	public abstract void keyRelease(int key);
	public abstract void mousePress(int btn, int x, int y);
	public abstract void mouseRelease(int btn, int x, int y);
	public abstract boolean drawWorld();
	public abstract boolean pauseGame();
}
