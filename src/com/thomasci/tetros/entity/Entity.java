package com.thomasci.tetros.entity;

import com.thomasci.tetros.screen.GameScreen;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class Entity {
	protected World world;
	protected int width, height;
	protected float x, y, vx, vy, health, maxHealth, bounciness;
	protected boolean onGround = false;
	
	public Entity(World world, float x, float y) {
		this.world = world;
		this.x = x;
		this.y = y;
		health = 1;
		maxHealth = 1;
		bounciness = 0;
		width = 1;
		height = 1;
	}
	
	public void tick(float dt) {
		doPhysics(dt);
	}
	
	protected final void doPhysics(float dt) {
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
		int height = this.height + (y - ((int) y) != 0 ? 1 : 0);
		if (vx < 0) {
			for (int i = 0; i < height; i++) {
				if (!world.getTileAt((int) x, (int) y + i).isAir()) {
					x = ((int) x) + 1;
					vx = 0;
				}
			}
		} else if (vx > 0) {
			for (int i = 0; i < height; i++) {
				if (!world.getTileAt((int) x + this.width, (int) y + i).isAir()) {
					x = (int) x;
					vx = 0;
				}
			}
		}
		
		//move out of ground / ceiling
		move(0, vy * dt);
		if (oldy != y) onGround = false;
		int width = this.width + (x - ((int) x) != 0 ? 1 : 0);
		if (vy > 0) {
			for (int i = 0; i < width; i++) {
				if (!world.getTileAt((int) x + i, (int) y + this.height).isAir()) {
					y = ((int) y);
					vy *= -bounciness * 0.8f;
				}
			}
		} else if (vy < 0) { //falling
			Tile t;
			for (int i = 0; i < width; i++) {
				t = world.getTileAt((int) x + i, (int) y);
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
	
	public void onSpawn() {}
	
	public void onKill() {}
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
		this.x = Math.round(this.x * 16.0f) / 16.0f;
		if (this.x < 0) this.x += world.getWidth();
		this.x = this.x % world.getWidth();
	}
	
	public void push(float vx, float vy) {
		this.vx += vx;
		this.vy += vy;
	}
	
	public World getWorld() {
		return world;
	}
	
	public float getX() {
		return x;
	}
	
	public float getNormX(float viewX) {
		if (viewX > GameScreen.instance().getImage().getViewWidth() &&
				viewX < world.getWidth() - GameScreen.instance().getImage().getViewWidth()) return x;
		if (viewX <= GameScreen.instance().getImage().getViewWidth() &&
				x >= world.getWidth() - GameScreen.instance().getImage().getViewWidth()) return x - world.getWidth();
		if (viewX >= world.getWidth() - GameScreen.instance().getImage().getViewWidth() &&
				x <= GameScreen.instance().getImage().getViewWidth()) return x + world.getWidth();
		return x;
	}
	
	public float getVX() {
		return vx;
	}
	
	public float getY() {
		return y;
	}
	
	public float getVY() {
		return vy;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getHealth() {
		return health;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public void heal(int amount) {
		if (amount < 0) return;
		health += amount;
		if (health > maxHealth) health = maxHealth;
	}
	
	public void damage(int amount) {
		if (amount < 0) return;
		health -= amount;
	}
	
	public void kill() {
		health = 0;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
}
