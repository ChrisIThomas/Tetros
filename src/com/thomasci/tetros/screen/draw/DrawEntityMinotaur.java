package com.thomasci.tetros.screen.draw;

import java.awt.Image;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityMinotaur;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;

public class DrawEntityMinotaur extends DrawEntity {
	private final Image ents;
	
	public DrawEntityMinotaur() {
		ents = GameImages.getImage("ents");
	}
	
	@Override
	public void drawEntity(Entity e, float viewX, int offsetX, int offsetY) {
		int x = (int) (e.getNormX(viewX) * 16) - offsetX;
		if (x >= -e.getWidth() * 16 && x < GameScreen.instance().getImage().getWidth()) {
			GameScreen screen = GameScreen.instance();
			int scrHeight = screen.getImage().getHeight();
			EntityMinotaur m = (EntityMinotaur) e;
			int frame = m.getFrame() * 8;
			if (m.isFacingRight()) {
				screen.getImage().drawImage(ents,
						(int) (m.getNormX(viewX) * 16) - offsetX, -(int) (m.getY() * 16) + scrHeight - 48 + offsetY, 32, 48,
						frame, 32, 8, 12
				);
			} else {
				screen.getImage().drawImage(ents,
						(int) (m.getNormX(viewX) * 16) - offsetX + 32, -(int) (m.getY() * 16) + scrHeight - 48 + offsetY, -32, 48,
						frame, 32, 8, 12
				);
			}
			Globals.renderCount++;
			((EntityMinotaur) e).setOnScreen(true);
		} else {
			((EntityMinotaur) e).setOnScreen(false);
		}
	}
}
