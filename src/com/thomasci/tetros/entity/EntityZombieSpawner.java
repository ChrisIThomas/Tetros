package com.thomasci.tetros.entity;

import java.util.Random;

import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class EntityZombieSpawner extends Entity {
	private float spawnTime = 0;
	
	public EntityZombieSpawner(World world, float x, float y) {
		super(world, x, y);
	}
	
	public void tick(float dt) {
		spawnTime -= dt;
		if (spawnTime <= 0) {
			if (world.countEntitiesNear(x, y, 12, EntityZombie.class) < 8) {
				Random r = world.getRandom();
				float sx = x - 3.0f + r.nextFloat() * 6.0f;
				world.spawnEntity(new EntityZombie(world, sx, world.getTopHeight((int) sx) + 1));
				spawnTime = 5.0f + r.nextFloat() * 10.0f;
			} else {
				spawnTime = 5.0f + world.getRandom().nextFloat() * 10.0f;
			}
		}
	}
	
	public void prepareArea() {
		int sx = (int) x;
		world.setTileAt(sx, (int) y, Tile.ZOM00);
		world.setTileAt(sx + 1, (int) y, Tile.ZOM10);
		world.setTileAt(sx, (int) y + 1, Tile.ZOM01);
		world.setTileAt(sx + 1, (int) y + 1, Tile.ZOM11);
		
		Random r = world.getRandom();
		move(-2, 0);
		world.setTileAt((int) x, world.getTopHeight((int) x) + 1, Tile.GRAVE);
		if (r.nextBoolean()) {
			move(-2, 0);
			world.setTileAt((int) x, world.getTopHeight((int) x) + 1, Tile.GRAVE);
			move(2, 0);
		}
		move(5, 0);
		world.setTileAt((int) x, world.getTopHeight((int) x) + 1, Tile.GRAVE);
		if (r.nextBoolean()) {
			move(2, 0);
			world.setTileAt((int) x, world.getTopHeight((int) x) + 1, Tile.GRAVE);
			move(-2, 0);
		}
		
		y += 2;
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
