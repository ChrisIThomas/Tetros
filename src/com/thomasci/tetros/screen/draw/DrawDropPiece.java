package com.thomasci.tetros.screen.draw;

import java.awt.Image;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityDropPiece;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;

public class DrawDropPiece extends DrawEntity {
	private final Image tiles;
	
	public DrawDropPiece() {
		tiles = GameImages.getImage("tiles");
	}
	
	@Override
	public void drawEntity(Entity e, float viewX, int offsetX, int offsetY) {
		int x = (int) (e.getNormX(viewX) * 16) - offsetX;
		if (x >= -e.getWidth() * 16 && x < GameScreen.instance().getImage().getWidth()) {
			GameScreen screen = GameScreen.instance();
			int scrHeight = screen.getImage().getHeight();
			EntityDropPiece p = (EntityDropPiece) e;
			int ix = p.getTile().getIcon();
			int iy = ix / 16 * 4;
			ix = ix % 16 * 4;
			screen.getImage().drawImage(tiles,
					x, -(int) (p.getY() * 16) + scrHeight - 16 + offsetY, 16, 16,
					ix, iy, 4, 4
			);
			Globals.renderCount++;
		}
	}
}
