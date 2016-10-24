package com.thomasci.tetros.screen.draw;

import java.awt.Image;

import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityParticle;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;

public class DrawEntityParticle extends DrawEntity {
	private final Image particle;
	
	public DrawEntityParticle() {
		particle = GameImages.getImage("particle");
	}
	
	@Override
	public void drawEntity(Entity e, float viewX, int offsetX, int offsetY) {
		int x = (int) (e.getNormX(viewX) * 16) - offsetX;
		if (x >= -e.getWidth() * 16 && x < GameScreen.instance().getImage().getWidth()) {
			GameScreen screen = GameScreen.instance();
			int scrHeight = screen.getImage().getHeight();
			EntityParticle p = (EntityParticle) e;
			int ix = p.getType();
			int iy = ix / 4 * 2;
			ix = ix % 4 * 2;
			screen.getImage().drawImage(particle,
					(int) (p.getNormX(viewX) * 16) - offsetX, -(int) (p.getY() * 16) + scrHeight - 4 + offsetY, 4, 4,
					ix, iy, 2, 2
			);
		}
	}
}
