package com.thomasci.tetros.entity;

import com.thomasci.tetros.world.World;

public class EntityZombie extends EntityLiving {
	private float wander = 0.0f;
	
	public EntityZombie(World world, float x, float y) {
		super(world, x, y);
		width = 1;
		height = 2;
		health = 6;
		maxHealth = 6;
		attackDamage = 2;
		coins = 10;
		sound = "zombie";
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
					frame = frame % 5;
					if (frame < 1) frame = 1;
				}
			} else {
				if (useTime > 0.5f) frame = 5;
				else frame = 0;
			}
		} else {
			if (vy >= 0) frame = 1;
			else frame = 2;
		}
	}
	
	private void doAI(float dt) {
		float dist = world.distance(x, world.getPlayer().getX());
		if (world.getPlayer().isDead()) dist = Float.MAX_VALUE;
		if (dist <= 16.0f) {
			int direction = world.directionTo(x, world.getPlayer().getX());
			if (onGround) {
				if (direction > 0) facingRight = true;
				else if (direction < 0) facingRight = false;
			}
			if (dist > 1) {
				if (onGround) {
					tryJump(dt, direction);
					push(direction * 0.875f, 0);
				} else {
					if (Math.abs(vx) < 2.0f) push(direction * 2.0f, 0);
				}
				
			} else {
				if (useTime <= 0) doAttack();
			}
		} else {
			if (world.getRandom().nextInt(10) == 0) {
				int rand = world.getRandom().nextInt(9);
				if (rand < 3) wander = -0.875f;
				else if (rand < 6) wander = 0.875f;
				else wander = 0;
				tryJump(dt, (int) Math.signum(wander));
			}
			if (wander > 0) facingRight = true;
			else if (wander < 0) facingRight = false;
			if (Math.abs(vx) < 2.0f) push(wander, 0);
		}
	}
	
	private void tryJump(float dt, int direction) {
		if (direction == 0) return;
		int height = this.height + (y - ((int) y) != 0 ? 1 : 0);
		if (direction < 0) {
			for (int i = 0; i < height; i++) {
				if (!world.getTileAt((int) (x - 0.5f), (int) y + i).isAir()) {
					vy = 0;
					push(0, 10.75f);
				}
			}
		} else if (direction > 0) {
			for (int i = 0; i < height; i++) {
				if (!world.getTileAt((int) (x + 0.5f) + this.width, (int) y + i).isAir()) {
					vy = 0;
					push(0, 10.75f);
				}
			}
		}
	}
}
