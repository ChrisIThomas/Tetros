package com.thomasci.tetros.tile;

import com.thomasci.tetros.entity.EntityCavemanSpawner;
import com.thomasci.tetros.world.World;

public class TileCavemanSpawner extends Tile {
	public TileCavemanSpawner(int id) {
		super(id, 31);
	}
	
	public void updateTile(World world, int x, int y) {
		world.setTileAt(x, y, Tile.AIR);
		EntityCavemanSpawner spawner = new EntityCavemanSpawner(world, x, y);
		world.spawnEntity(spawner);
		spawner.prepareArea();
	}
}
