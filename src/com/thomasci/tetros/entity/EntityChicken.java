package com.thomasci.tetros.entity;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.item.Item;
import com.thomasci.tetros.world.World;

public class EntityChicken extends EntityLiving {
	private float wander = 0.0f;
	private boolean rooster;
	
	public EntityChicken(World world, float x, float y) {
		super(world, x, y);
		width = 1;
		height = 1;
		health = 4;
		maxHealth = 4;
		coins = 2;
		rooster = world.getRandom().nextBoolean();
		sound = "chicken";
	}
	
	@Override
	public void tick(float dt) {
		float px = x;
		super.tick(dt);
		
		doAI(dt);
		
		if (onGround) {
			if (x != px) {
				if (vx == 0) frame = 0;
				else {
					frame += Math.abs(vx) * dt * 2.5f;
					frame = frame % 2;
				}
			}
		} else {
			if (vy != 0) frame = 0;
		}
	}
	
	private void doAI(float dt) {
		if (world.getRandom().nextInt(10) == 0) {
			int rand = world.getRandom().nextInt(12);
			if (world.getRandom().nextFloat() <= 0.1f) {
				if (rand < 5) wander = -0.875f;
				else if (rand < 10) wander = 0.875f;
				else wander = 0;
			}
			tryJump(dt, (int) Math.signum(wander));
		}
		if (wander > 0) facingRight = true;
		else if (wander < 0) facingRight = false;
		if (Math.abs(vx) < 2.0f) push(wander, 0);
	}
	
	private void tryJump(float dt, int direction) {
		if (direction == 0) return;
		int height = this.height + (y - ((int) y) != 0 ? 1 : 0);
		if (direction < 0) {
			for (int i = 0; i < height; i++) {
				if (!world.getTileAt((int) (x - 0.5f), (int) y + i).isAir()) {
					vy = 0;
					push(0, 5.25f);
				}
			}
		} else if (direction > 0) {
			for (int i = 0; i < height; i++) {
				if (!world.getTileAt((int) (x + 0.5f) + this.width, (int) y + i).isAir()) {
					vy = 0;
					push(0, 5.25f);
				}
			}
		}
	}
	
	@Override
	public void onSpawn() {
		Globals.chickens += 1;
	}
	
	@Override
	public void onKill() {
		world.spawnEntity(new EntityItem(world, x, y, Item.DRUMSTICK));
		Globals.chickens -= 1;
	}
	
	public boolean isRooster() {
		return rooster;
	}
}
