package com.thomasci.tetros.tile;

import com.thomasci.tetros.XOR;
import com.thomasci.tetros.world.World;

public class TileDirt extends Tile {
	public TileDirt(int id) {
		super(id, 1);
	}
	
	@Override
	public void updateTile(World world, int x, int y) {
		if (world.getTileAt(x, y + 1) == Tile.AIR) {
			world.setTileAt(x, y + 1, Tile.LONGGRASS);
		}
	}
	
	@Override
	public int getIcon(World world, int x, int y, long time) {
		if (!world.getTileAt(x, y + 1).isAir()) return 5 + (int) ((XOR.random(x) + y) % 4);
		boolean l = world.getTileAt(x - 1, y).isAir();
		boolean r = world.getTileAt(x + 1, y).isAir();
		if (l == r) return 1 + (int) ((XOR.random(x) + y) % 2);
		if (l) return 3;
		if (r) return 4;
		return 5 + (int) ((XOR.random(x) + y) % 4);
	}
}
