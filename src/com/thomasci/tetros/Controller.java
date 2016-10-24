package com.thomasci.tetros;

public interface Controller {
	public void keyPress(int key);
	public void keyRelease(int key);
	public void keyDown(int key, long time);
	public float getViewX();
	public float getViewY();
}
