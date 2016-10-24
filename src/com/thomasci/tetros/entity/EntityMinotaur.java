package com.thomasci.tetros.entity;

import java.util.Random;

import com.thomasci.tetros.screen.GameSounds;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class EntityMinotaur extends EntityLiving {
	private float wander = 0.0f;
	private float chargeTime = 5.0f;
	private float waitTime = 0.0f;
	private float px;
	private int charges = 6;
	private boolean breakTile;
	
	public EntityMinotaur(World world, float x, float y) {
		super(world, x, y);
		width = 2;
		height = 3;
		health = 100;
		maxHealth = 100;
		attackDamage = 4;
		coins = 1000;
		sound = "minotaur";
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		
		if (waitTime > 0) {
			waitTime -= dt;
			Random r = world.getRandom();
			if (r.nextFloat() <= 0.05f) {
				EntityParticle p = new EntityParticle(world, x + 0.5f + r.nextFloat(), y + 2.5f, 5, 2.0f - r.nextFloat());
				p.push(r.nextFloat() * 10 - 5.0f, r.nextFloat() * 5.0f + 5.0f);
				world.spawnParticle(p);
			}
		} else {
			doAI(dt);
		}
		
		if (onGround) {
			if (vx == 0) frame = 0;
			else {
				frame += Math.abs(vx) * dt * 1.5f;
				frame = frame % 5;
			}
		} else {
			if (vy >= 0) frame = 1;
			else frame = 2;
		}
		px = this.x;
	}
	
	private void doAI(float dt) {
		if (charges <= 0) {
			waitTime = 6.0f;
			charges = 6;
		}
		
		float dist = world.distance(x + 0.5f, world.getPlayer().getX());
		if (world.getPlayer().isDead()) dist = Float.MAX_VALUE;
		if (dist <= 100000.0f) {
			chargeTime -= dt;
			int direction = world.directionTo(x, world.getPlayer().getX());
			if (onGround) {
				if (direction > 0) facingRight = true;
				else if (direction < 0) facingRight = false;
			}
			if (dist > 1) {
				if (onGround) {
					if (chargeTime <= 0) {
						charges--;
						GameSounds.getSound("place").play();
						push(direction * 20.0f, 5.0f);
						chargeTime = 5.0f;
						breakTile = true;
					} else {
						if (breakTile) {
							if (px == x) {
								smashTiles();
								chargeTime = 0.5f;
							}
						}
						breakTile = false;
						tryJump(dt, direction);
						push(direction * 2.0f, 0);
					}
				} else {
					if (Math.abs(vx) < 7.0f) push(direction * 2.0f, 0);
				}
			}
			if (dist <= 4) {
				if (useTime <= 0) doAttack();
			}
		} else {
			if (world.getRandom().nextInt(10) == 0) {
				int rand = world.getRandom().nextInt(9);
				if (rand < 3) wander = -4.0f;
				else if (rand < 6) wander = 4.0f;
				else wander = 0;
				wander = -4.0f;
				tryJump(dt, (int) Math.signum(wander));
			}
			if (wander > 0) facingRight = true;
			else if (wander < 0) facingRight = false;
			if (Math.abs(vx) < 7.0f) push(wander, 0);
		}
	}
	
	private void smashTiles() {
		boolean centered = this.x % 1 == 0;;
		int x = (int) (this.x + (facingRight ? (centered ? width : width + 1) : -1));
		int y = Math.round(this.y + height);
		for (int i = height; i >= 0; i--) {
			if (!world.getTileAt(x, y).isAir()) {
				world.setTileAt(x, y, Tile.AIR);
				Random r = world.getRandom();
				EntityParticle p;
				for (int j = 0; j < 8; j++) {
					p = new EntityParticle(world, x + r.nextFloat() * 0.75f, y + r.nextFloat() * 0.75f, 4, 2.0f - r.nextFloat());
					p.push(r.nextFloat() * 10 - 5.0f, r.nextFloat() * 10 - 2.5f);
					world.spawnParticle(p);
				}
			}
			y -= 1;
		}
	}
	
	private void tryJump(float dt, int direction) {
		if (direction == 0) return;
		int height = this.height + (y - ((int) y) != 0 ? 1 : 0);
		if (direction < 0) {
			for (int i = 0; i < height; i++) {
				if (!world.getTileAt((int) (x - 1.5f), (int) y + i).isAir()) {
					vy = 0;
					push(0, 10.75f);
				}
			}
		} else if (direction > 0) {
			for (int i = 0; i < height; i++) {
				if (!world.getTileAt((int) (x + 2.5f) + this.width, (int) y + i).isAir()) {
					vy = 0;
					push(0, 10.75f);
				}
			}
		}
	}
	
	@Override
	public void doAttack() {
		useTime = 0.5f;
		Entity e = world.getEntityAt(x + 0.5f, y + 1, 2, 2, EntityPlayer.class, this);
		if (e != null) {
			e.damage(attackDamage);
			e.push((this.facingRight ? 20.0f : -20.0f), 10.0f);
		}
	}
}
