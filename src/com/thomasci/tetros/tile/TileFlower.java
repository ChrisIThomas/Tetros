package com.thomasci.tetros.tile;

import com.thomasci.tetros.XOR;
import com.thomasci.tetros.world.World;

public class TileFlower extends Tile {
	public TileFlower(int id) {
		super(id, 11);
	}
	
	public void updateTile(World world, int x, int y) {
		if (world.getTileAt(x, y - 1) != Tile.DIRT) {
			world.setTileAt(x, y, Tile.AIR);
		}
	}
	
	@Override
	public int getIcon(World world, int x, int y, long time) {
		return 11 + (int) (XOR.random(x + y) % 3);
	}
}
