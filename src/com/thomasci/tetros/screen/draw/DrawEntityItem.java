package com.thomasci.tetros.screen.draw;

import java.awt.Image;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityItem;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;

public class DrawEntityItem extends DrawEntity {
	private final Image items;
	
	public DrawEntityItem() {
		items = GameImages.getImage("items");
	}
	
	@Override
	public void drawEntity(Entity e, float viewX, int offsetX, int offsetY) {
		int x = (int) (e.getNormX(viewX) * 16) - offsetX;
		if (x >= -e.getWidth() * 16 && x < GameScreen.instance().getImage().getWidth()) {
			GameScreen screen = GameScreen.instance();
			int scrHeight = screen.getImage().getHeight();
			EntityItem i = (EntityItem) e;
			int ix = i.getItem().getIcon();
			int iy = ix / 16 * 4;
			ix = ix % 16 * 4;
			screen.getImage().drawImage(items,
					(int) (i.getNormX(viewX) * 16) - offsetX, -(int) (i.getY() * 16) + scrHeight - 16 + offsetY, 16, 16,
					ix, iy, 4, 4
			);
			Globals.renderCount++;
		}
	}
}
