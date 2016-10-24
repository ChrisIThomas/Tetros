package com.thomasci.tetros.screen.draw;

import java.awt.Image;

import com.thomasci.tetros.Game;
import com.thomasci.tetros.Globals;
import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;

public class DrawEntityCavemanSpawner extends DrawEntity {
	private final Image ents;
	
	public DrawEntityCavemanSpawner() {
		ents = GameImages.getImage("ents");
	}
	
	@Override
	public void drawEntity(Entity e, float viewX, int offsetX, int offsetY) {
		if (Game.isDebugging()) {
			int x = (int) (e.getNormX(viewX) * 16) - offsetX;
			if (x >= -e.getWidth() * 16 && x < GameScreen.instance().getImage().getWidth()) {
				GameScreen screen = GameScreen.instance();
				int scrHeight = screen.getImage().getHeight();
				screen.getImage().drawImage(ents,
						(int) (e.getNormX(viewX) * 16) - offsetX, -(int) (e.getY() * 16) + scrHeight - 16 + offsetY, 16, 16,
						24, 12, 4, 4
				);
				Globals.renderCount++;
			}
		}
	}
}
