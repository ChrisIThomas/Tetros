package com.thomasci.tetros.tile;

import com.thomasci.tetros.XOR;
import com.thomasci.tetros.world.World;

public class TileObelisk extends Tile {
	public TileObelisk(int id, int icon) {
		super(id, icon);
	}
	
	@Override
	public int getIcon(World world, int x, int y, long time) {
		return icon + (int) (XOR.random(x + y) % 2);
	}
}
