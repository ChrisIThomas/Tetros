package com.thomasci.tetros.tile;

import com.thomasci.tetros.entity.EntityZombieSpawner;
import com.thomasci.tetros.world.World;

public class TileZombieSpawner extends Tile {
	public TileZombieSpawner(int id) {
		super(id, 23);
	}
	
	public void updateTile(World world, int x, int y) {
		world.setTileAt(x, y, Tile.AIR);
		EntityZombieSpawner spawner = new EntityZombieSpawner(world, x, y);
		world.spawnEntity(spawner);
		spawner.prepareArea();
	}
}
