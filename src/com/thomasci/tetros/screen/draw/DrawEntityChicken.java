package com.thomasci.tetros.screen.draw;

import java.awt.Image;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityChicken;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;

public class DrawEntityChicken extends DrawEntity {
	private final Image ents;
	
	public DrawEntityChicken() {
		ents = GameImages.getImage("ents");
	}
	
	@Override
	public void drawEntity(Entity e, float viewX, int offsetX, int offsetY) {
		int x = (int) (e.getNormX(viewX) * 16) - offsetX;
		if (x >= -e.getWidth() * 16 && x < GameScreen.instance().getImage().getWidth()) {
			GameScreen screen = GameScreen.instance();
			int scrHeight = screen.getImage().getHeight();
			EntityChicken c = (EntityChicken) e;
			int frame = c.getFrame() * 4;
			if (c.isFacingRight()) {
				screen.getImage().drawImage(ents,
						(int) (c.getNormX(viewX) * 16) - offsetX, -(int) (c.getY() * 16) + scrHeight - 16 + offsetY, 16, 16,
						frame, c.isRooster() ? 24 : 28, 4, 4
				);
			} else {
				screen.getImage().drawImage(ents,
						(int) (c.getNormX(viewX) * 16) - offsetX + 16, -(int) (c.getY() * 16) + scrHeight - 16 + offsetY, -16, 16,
						frame, c.isRooster() ? 24 : 28, 4, 4
				);
			}
			Globals.renderCount++;
			((EntityChicken) e).setOnScreen(true);
		} else {
			((EntityChicken) e).setOnScreen(false);
		}
	}
}
