package com.thomasci.tetros.tile;

import com.thomasci.tetros.entity.EntityCoinMachine;
import com.thomasci.tetros.world.World;

public class TileCoinMachine extends Tile {
	public TileCoinMachine(int id) {
		super(id, 19);
	}
	
	public void updateTile(World world, int x, int y) {
		world.setTileAt(x, y, Tile.AIR);
		world.spawnEntity(new EntityCoinMachine(world, x, y));
	}
}
