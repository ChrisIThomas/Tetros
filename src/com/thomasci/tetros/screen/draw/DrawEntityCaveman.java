package com.thomasci.tetros.screen.draw;

import java.awt.Image;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityCaveman;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;

public class DrawEntityCaveman extends DrawEntity {
	private final Image ents;
	
	public DrawEntityCaveman() {
		ents = GameImages.getImage("ents");
	}
	
	@Override
	public void drawEntity(Entity e, float viewX, int offsetX, int offsetY) {
		int x = (int) (e.getNormX(viewX) * 16) - offsetX;
		if (x >= -e.getWidth() * 16 && x < GameScreen.instance().getImage().getWidth()) {
			GameScreen screen = GameScreen.instance();
			int scrHeight = screen.getImage().getHeight();
			EntityCaveman p = (EntityCaveman) e;
			int frame = p.getFrame() * 4;
			if (p.isFacingRight()) {
				screen.getImage().drawImage(ents,
						(int) (p.getNormX(viewX) * 16) - offsetX, -(int) (p.getY() * 16) + scrHeight - 32 + offsetY, 16, 32,
						frame, 8, 4, 8
				);
			} else {
				screen.getImage().drawImage(ents,
						(int) (p.getNormX(viewX) * 16) - offsetX + 16, -(int) (p.getY() * 16) + scrHeight - 32 + offsetY, -16, 32,
						frame, 8, 4, 8
				);
			}
			Globals.renderCount++;
			((EntityCaveman) e).setOnScreen(true);
		} else {
			((EntityCaveman) e).setOnScreen(false);
		}
	}
}
