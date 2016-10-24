package com.thomasci.tetros.item;

import java.util.Random;

import com.thomasci.tetros.entity.EntityLiving;
import com.thomasci.tetros.entity.EntityParticle;
import com.thomasci.tetros.screen.GameSounds;
import com.thomasci.tetros.tile.Tile;

public class ItemShovel extends Item {
	public ItemShovel(int id) {
		super(id, 1);
	}
	
	public void onUse(EntityLiving e) {
		boolean centered = e.getX() % 1 == 0;;
		int x = (int) (e.getX() + (e.isFacingRight() ? (centered ? e.getWidth() : e.getWidth() + 1) : -1));
		int y = Math.round(e.getY());
		for (int i = e.getHeight() - 1; i >= -1; i--) {
			if (e.getWorld().getTileAt(x, y + i) == Tile.DIRT) {
				e.getWorld().setTileAt(x, y + i, Tile.AIR);
				y += i;
				Random r = e.getWorld().getRandom();
				EntityParticle p;
				for (int j = 0; j < 8; j++) {
					p = new EntityParticle(e.getWorld(), x + r.nextFloat() * 0.75f, y + r.nextFloat() * 0.75f, 1, 2.0f - r.nextFloat());
					p.push(r.nextFloat() * 10 - 5.0f, r.nextFloat() * 10 - 2.5f);
					e.getWorld().spawnParticle(p);
				}
				GameSounds.getSound("dig").play();
				return;
			}
		}
	}
}
