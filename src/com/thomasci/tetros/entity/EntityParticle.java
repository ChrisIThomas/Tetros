package com.thomasci.tetros.entity;

import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class EntityParticle extends Entity {
	private int type;
	private float life;
	
	public EntityParticle(World world, float x, float y, int type, float life) {
		super(world, x, y);
		this.type = type;
		this.life = life;
	}
	
	@Override
	public void tick(float dt) {
		life -= dt;
		if (life <= 0) {
			kill();
			return;
		}
		
		if (bounciness < 0) bounciness = -bounciness;
		float oldy = y;
		boolean wasOnGround = onGround;
		if (onGround) {
			vx *= 0.75f;
		}
		if (vx <= 0.01f && vx >= -0.01f) vx = 0;
		if (vy <= 0.01f && vy >= -0.01f) vy = 0;
		vy -= 19.6f * dt;
		if (vx > 23) vx = 23;
		else if (vx < -23) vx = -23;
		if (vy > 23) vy = 23;
		else if (vy < -23) vy = -23;
		
		//move out of walls
		move(vx * dt, 0);
		if (vx < 0) {
			if (!world.getTileAt((int) x, (int) (y + 0.125f)).isAir()) {
				x = ((int) x) + 1;
				vx = 0;
			}
		} else if (vx > 0) {
			if (!world.getTileAt((int) (x + 0.25f), (int) (y + 0.125f)).isAir()) {
				x = (int) x + 0.75f;
				vx = 0;
			}
		}
		
		//move out of ground / ceiling
		move(0, vy * dt);
		if (oldy != y) onGround = false;
		if (vy > 0) {
			if (!world.getTileAt((int) (x + 0.125f), (int) (y + 0.25f)).isAir()) {
				y = ((int) y + 0.75f);
				vy *= -bounciness * 0.8f;
			}
		} else if (vy < 0) { //falling
			/*if (!world.getTileAt((int) (x + 0.125f), (int) y).isAir()) {
				y = ((int) y) + 1;
				vy *= -bounciness * 0.8f;
				onGround = true;
				if (vy <= 0.98f) vy = 0;
			}*/
			Tile t;
			for (int i = 0; i < width; i++) {
				t = world.getTileAt((int) (x + 0.125f), (int) y);
				if (!t.isAir()) {
					y = ((int) y) + 1;
					vy *= -bounciness * 0.8f;
					onGround = true;
					if (vy <= 0.98f) vy = 0;
				} else if (t.isOneWay()) {
					if ((int) oldy > (int) y) {
						y = ((int) y) + 1;
						vy *= -bounciness * 0.8f;
						onGround = true;
						if (vy <= 0.98f) vy = 0;
					}
				}
			}
		}
		
		//make sure if the entity tries to move up but is stopped by a ceiling they are still considered "on the ground".
		if (oldy == y && wasOnGround) onGround = true;
	}
	
	@Override
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
		if (this.x < 0) this.x += world.getWidth();
		this.x = this.x % world.getWidth();
	}
	
	public int getType() {
		return type;
	}
}
