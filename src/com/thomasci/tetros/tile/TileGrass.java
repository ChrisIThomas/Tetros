package com.thomasci.tetros.tile;

import com.thomasci.tetros.XOR;
import com.thomasci.tetros.world.World;

public class TileGrass extends Tile {
	public TileGrass(int id) {
		super(id, 9);
	}
	
	public void updateTile(World world, int x, int y) {
		if (world.getTileAt(x, y - 1) != Tile.DIRT) {
			world.setTileAt(x, y, Tile.AIR);
		}
	}
	
	@Override
	public int getIcon(World world, int x, int y, long time) {
		return 9 + (int) ((XOR.random(x + y) % 5) == 0 ? 1 : 0);
	}
}
