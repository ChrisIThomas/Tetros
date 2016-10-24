package com.thomasci.tetros.entity;

import java.util.Random;

import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class EntityCavemanSpawner extends Entity {
	private float spawnTime = 0;
	
	public EntityCavemanSpawner(World world, float x, float y) {
		super(world, x, y);
	}
	
	public void tick(float dt) {
		spawnTime -= dt;
		if (spawnTime <= 0) {
			if (world.countEntitiesNear(x, y, 12, EntityCaveman.class) < 8) {
				Random r = world.getRandom();
				float sx = x - 3.0f + r.nextFloat() * 6.0f;
				world.spawnEntity(new EntityCaveman(world, sx, world.getTopHeight((int) sx) + 1));
				spawnTime = 5.0f + r.nextFloat() * 10.0f;
			} else {
				spawnTime = 5.0f + world.getRandom().nextFloat() * 10.0f;
			}
		}
	}
	
	public void prepareArea() {
		int sx = (int) x;
		world.setTileAt(sx, (int) y, Tile.CAV0);
		world.setTileAt(sx + 1, (int) y, Tile.CAV1);
		
		Random r = world.getRandom();
		move(-2, 0);
		world.setTileAt((int) x, world.getTopHeight((int) x) + 1, Tile.CAVEMANRUBBLE);
		if (r.nextBoolean()) {
			move(-2, 0);
			world.setTileAt((int) x, world.getTopHeight((int) x) + 1, Tile.CAVEMANRUBBLE);
			move(2, 0);
		}
		move(5, 0);
		world.setTileAt((int) x, world.getTopHeight((int) x) + 1, Tile.CAVEMANRUBBLE);
		if (r.nextBoolean()) {
			move(2, 0);
			world.setTileAt((int) x, world.getTopHeight((int) x) + 1, Tile.CAVEMANRUBBLE);
			move(-2, 0);
		}
		
		y += 1;
		x = sx + 0.5f;
		x = x % world.getWidth();
		spawnTime = 5.0f + r.nextFloat() * 10.0f;
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
