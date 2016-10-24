package com.thomasci.tetros.state;

import java.awt.Color;
import java.awt.FontMetrics;

import com.thomasci.tetros.Game;
import com.thomasci.tetros.screen.GameScreen;

public class StateQuit extends GameState {
	@Override
	public void initialize() {}
	
	@Override
	public void finalize() {}
	
	@Override
	public void tick(float dt) {}
	
	@Override
	public void draw(long time) {
		GameScreen screen = GameScreen.instance();
		FontMetrics metrics = screen.getImage().getGraphics().getFontMetrics(screen.getImage().getGraphics().getFont());
		screen.getImage().drawText(
				"Quit: Y/N",
				(screen.getWidth() - metrics.stringWidth("Quit: Y/N")) / 4,
				(screen.getHeight() - metrics.getHeight()) / 4,
				Color.WHITE
		);
	}
	
	@Override
	public void keyPress(int key) {
		if (key == 89) { //Y
			Game.instance().end();
		} else if (key == 78 || key == 27) { //N
			Game.instance().swapToWorldState();
		}
	}
	
	@Override
	public void keyDown(int key, long time) {}
	
	@Override
	public void keyRelease(int key) {}
	
	@Override
	public void mousePress(int btn, int x, int y) {}
	
	@Override
	public void mouseRelease(int btn, int x, int y) {}
	
	@Override
	public boolean drawWorld() {
		return true;
	}
	
	@Override
	public boolean pauseGame() {
		return true;
	}
}
