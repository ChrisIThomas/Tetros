package com.thomasci.tetros.tile;

import com.thomasci.tetros.XOR;
import com.thomasci.tetros.world.World;

public class TileStone extends Tile {
	public TileStone(int id) {
		super(id, 14);
	}
	
	@Override
	public int getIcon(World world, int x, int y, long time) {
		return 14 + (int) ((XOR.random(x) + y) % 4);
	}
}
