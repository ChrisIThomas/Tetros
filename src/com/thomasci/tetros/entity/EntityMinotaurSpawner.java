package com.thomasci.tetros.entity;

import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class EntityMinotaurSpawner extends Entity {
	private float spawnTime = 60.0f;
	private boolean spawned = false;
	
	public EntityMinotaurSpawner(World world, float x, float y) {
		super(world, x, y);
	}
	
	public void tick(float dt) {
		if (!spawned) {
			spawnTime -= dt;
			if (spawnTime <= 0) {
				world.spawnEntity(new EntityMinotaur(world, x, world.getTopHeight((int) x) + 1));
				spawned = true;
			}
		}
	}
	
	public void prepareArea() {
		int sx = (int) x;
		
		move(-2, 0);
		int h = world.getTopHeight((int) x) + 1;
		world.setTileAt((int) x, h, Tile.OB0);
		world.setTileAt((int) x, h + 1, Tile.OB1);
		world.setTileAt((int) x, h + 2, Tile.OB2);
		move(5, 0);
		h = world.getTopHeight((int) x) + 1;
		world.setTileAt((int) x, h, Tile.OB0);
		world.setTileAt((int) x, h + 1, Tile.OB1);
		world.setTileAt((int) x, h + 2, Tile.OB2);
		
		x = sx + 0.5f;
		x = x % world.getWidth();
		spawnTime = 60.0f;
		spawned = false;
	}
	
	@Override
	public void damage(int amount) {}
	
	@Override
	public void kill() {}
	
	@Override
	public boolean isDead() {
		return false;
	}
}
