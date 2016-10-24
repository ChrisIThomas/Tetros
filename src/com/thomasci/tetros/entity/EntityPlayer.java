package com.thomasci.tetros.entity;

import com.thomasci.tetros.Controller;
import com.thomasci.tetros.Game;
import com.thomasci.tetros.item.Item;
import com.thomasci.tetros.screen.GameSounds;
import com.thomasci.tetros.world.World;

public class EntityPlayer extends EntityLiving implements Controller {
	public EntityPlayer(World world, float x, float y) {
		super(world, x, y);
		width = 1;
		height = 2;
		health = 20;
		maxHealth = 20;
	}
	
	@Override
	public void tick(float dt) {
		float px = x;
		super.tick(dt);
		if (useTime > 0) {
			frame = 6;
		} else if (onGround) {
			if (world.getState() != Game.instance().getCurrentState()) frame = 5;
			else if (x != px) {
				if (vx == 0) frame = 0;
				else {
					float pf = frame;
					frame += Math.abs(vx) * dt * 2.5f;
					frame = frame % 5;
					if (frame < 1) {
						frame = 1;
					}
					if (pf <= 2 && frame >= 2) {
						GameSounds.getSound("step").play();
					}
				}
			} else {
				frame = 0;
			}
		} else {
			if (vy >= 0) frame = 1;
			else frame = 2;
		}
	}
	
	@Override
	public void keyPress(int key) {
		if (key == 40) { //Down
			if (this.heldItem != null) {
				Entity e = this.dropHeldItem();
				if (e != null) {
					e.vx = vx / 2f;
					e.vy = vy / 2f;
				}
			} else {
				Entity e = world.getEntityNear(x + 0.5f, y, 1, EntityItem.class, this);
				if (e != null) {
					Item item = ((EntityItem) e).getItem();
					if (item != null) {
						if (this.giveItem(item)) {
							e.kill();
						}
					}
				}
			}
		} else if (key == 32) { //Space
			if (useTime <= 0) {
				useTime = 0.1f;
				if (this.heldItem != null) {
					this.heldItem.onUse(this);
				} else {
					Entity e = world.getEntityAt(x + (this.facingRight ? 0.75f : -0.75f), y + 1, 1, 2, EntityLiving.class, this);
					if (e != null) {
						e.damage(1);
						e.push((this.facingRight ? 10.0f : -10.0f), 2.0f);
					}
				}
			}
		}
	}
	
	@Override
	public void keyRelease(int key) {}
	
	@Override
	public void keyDown(int key, long time) {
		if (key == 37) { //Left
			if (this.onGround) {
				this.push(-2.5f, 0);
				facingRight = false;
			} else {
				if (vx > -2.33f) this.push(-2.0f, 0);
			}
		} else if (key == 39) { //Right
			if (this.onGround) {
				this.push(2.5f, 0);
				facingRight = true;
			} else {
				if (vx < 2.33f) this.push(2.0f, 0);
			}
		} else if (key == 38) { //Up
			if (this.onGround) {
				this.vy = 0;
				this.push(0, 12.0f);
				GameSounds.getSound("jump").play();
			}
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
	
	@Override
	public void damage(int amount) {
		super.damage(amount);
		GameSounds.getSound("hit").play();
	}
}
