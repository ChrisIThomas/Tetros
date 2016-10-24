package com.thomasci.tetros.tile;

import com.thomasci.tetros.XOR;
import com.thomasci.tetros.world.World;

public class TileWood extends Tile {
	public TileWood(int id) {
		super(id, 21);
	}
	
	@Override
	public int getIcon(World world, int x, int y, long time) {
		return 21 + (int) ((XOR.random(x) + y) % 2);
	}
}
