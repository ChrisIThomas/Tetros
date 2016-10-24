package com.thomasci.tetros.entity;

import java.util.ArrayList;

import com.thomasci.tetros.Controller;
import com.thomasci.tetros.screen.GameSounds;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class EntityDropPiece extends Entity implements Controller {
	private final Tile tile;
	
	public EntityDropPiece(World world, float x, float y, Tile tile) {
		super(world, x, y);
		this.tile = tile;
	}
	
	@Override
	public void tick(float dt) {
		if (this.onGround) {
			if (Math.abs(x) % 1 <= 0.5f) move(-x % 1, 0);
			else move(x % 1, 0);
			ArrayList<Entity> ents = world.getAllEntitiesNear(x, y, 0.5f, Entity.class, this);
			for (Entity e: ents) {
				e.kill();
			}
			onDrop();
			world.getState().setController(world.getPlayer());
			kill();
		} else {
			Tile t;
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
					t = world.getTileAt((int) x, (int) y + i);
					if (!t.isAir() || t.shouldDropOn()) {
						x = ((int) x) + 1;
						vx = 0;
					}
				}
			} else if (vx > 0) {
				for (int i = 0; i < height; i++) {
					t = world.getTileAt((int) x + this.width, (int) y + i);
					if (!t.isAir() || t.shouldDropOn()) {
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
					t = world.getTileAt((int) x + i, (int) y + this.height);
					if (!t.isAir() || t.shouldDropOn()) {
						y = ((int) y);
						vy *= -bounciness * 0.8f;
					}
				}
			} else if (vy < 0) { //falling
				for (int i = 0; i < width; i++) {
					t = world.getTileAt((int) x + i, (int) y);
					if (!t.isAir() || t.shouldDropOn()) {
						y = ((int) y) + 1;
						vy *= -bounciness * 0.8f;
						onGround = true;
						if (vy <= 0.98f) vy = 0;
					}
				}
			}
			
			//make sure if the entity tries to move up but is stopped by a ceiling they are still considered "on the ground".
			if (oldy == y && wasOnGround) onGround = true;
			vy = -5.0f;
			vx = 0;
		}
	}
	
	protected void onDrop() {
		world.setTileAt((int) x, (int) y, tile);
		GameSounds.getSound("place").play();
	}
	
	public Tile getTile() {
		return tile;
	}
	
	@Override
	public void keyPress(int key) {}
	
	@Override
	public void keyRelease(int key) {}
	
	@Override
	public void keyDown(int key, long time) {
		if (key == 37) { //Left
			if (!this.onGround) this.push(-5, 0);
		} else if (key == 39) { //Right
			if (!this.onGround) this.push(5, 0);
		} else if (key == 38) { //Up
			this.vy = -1.75f;
		} else if (key == 40) { //Down
			this.vy = -10.0f;
		}
	}
	
	@Override
	public float getViewX() {
		return getX();
	}
	
	@Override
	public float getViewY() {
		return getY();
	}
}
