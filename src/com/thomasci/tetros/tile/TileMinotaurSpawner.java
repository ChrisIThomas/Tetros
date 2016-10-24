package com.thomasci.tetros.tile;

import com.thomasci.tetros.entity.EntityMinotaurSpawner;
import com.thomasci.tetros.world.World;

public class TileMinotaurSpawner extends Tile {
	public TileMinotaurSpawner(int id) {
		super(id, 39);
	}
	
	public void updateTile(World world, int x, int y) {
		world.setTileAt(x, y, Tile.AIR);
		EntityMinotaurSpawner spawner = new EntityMinotaurSpawner(world, x, y);
		world.spawnEntity(spawner);
		spawner.prepareArea();
	}
}
