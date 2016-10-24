package com.thomasci.tetros.tile;

import com.thomasci.tetros.XOR;
import com.thomasci.tetros.world.World;

public class TileGrave extends Tile {
	public TileGrave(int id) {
		super(id, 23);
	}
	
	public void updateTile(World world, int x, int y) {
		if (world.getTileAt(x, y - 1).isAir()) {
			world.setTileAt(x, y, Tile.AIR);
		}
	}
	
	@Override
	public int getIcon(World world, int x, int y, long time) {
		return 23 + (int) (XOR.random(x + y) % 3);
	}
}
