package com.thomasci.tetros.entity;

import java.util.Random;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.item.Item;
import com.thomasci.tetros.screen.GameSounds;
import com.thomasci.tetros.world.World;

public class EntityLiving extends Entity {
	protected boolean facingRight, onScreen;
	protected Item heldItem;
	protected float frame, useTime, sndTime;
	protected int attackDamage, coins;
	protected String sound = "";
	
	public EntityLiving(World world, float x, float y) {
		super(world, x, y);
		health = 10;
		maxHealth = 10;
		attackDamage = 1;
		coins = 0;
		sndTime = 5.0f;
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		if (useTime > 0)
			useTime -= dt;
		if (sndTime > 0) {
			sndTime -= dt;
		} else if (sndTime <= 0) {
			if (!sound.isEmpty() && onScreen)
				GameSounds.getSound(sound).play();
			sndTime = world.getRandom().nextFloat() * 5.0f + 5.0f;
		}
	}
	
	@Override
	public void onKill() {
		Random r = world.getRandom();
		EntityParticle p;
		for (int i = 0; i < maxHealth; i++) {
			p = new EntityParticle(world, x + r.nextFloat() * 0.75f, y + r.nextFloat() * (height - 0.25f), 0, 4.0f);
			p.push(world.getRandom().nextFloat() * 20.0f - 10.0f, 5.0f + r.nextFloat() * 5.0f);
			world.spawnParticle(p);
		}
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}
	
	public Item getHeldItem() {
		return heldItem;
	}
	
	public int getFrame() {
		return (int) frame;
	}
	
	public boolean giveItem(Item item) {
		if (item == null) {
			if (heldItem != null) {
				if (!heldItem.onDrop(this)) return false;
				heldItem.dropItemAt(world, x, y);
				heldItem = null;
				return true;
			}
		} else {
			if (heldItem != null) {
				if (!heldItem.onDrop(this)) return false;
			}
			if (item.onEquip(this)) {
				if (heldItem != null) heldItem.dropItemAt(world, x, y);
				heldItem = item;
				useTime = 0;
				return true;
			}
		}
		return false;
	}
	
	public float getUseTime() {
		return useTime;
	}
	
	public void setUseTime(float time) {
		useTime = time;
	}
	
	/**
	 * Perform an attack, only targets the player
	 */
	public void doAttack() {
		useTime = 1.0f;
		Entity e = world.getEntityAt(x + (this.facingRight ? 1 : 0), y + 1, 1, 2, EntityPlayer.class, this);
		if (e != null) {
			e.damage(attackDamage);
			e.push((this.facingRight ? 5.0f : -5.0f), 0);
		}
	}
	
	public Item removeHeldItem() {
		if (heldItem != null) {
			Item i = heldItem;
			heldItem = null;
			useTime = 0;
			return i;
		}
		return null;
	}
	
	public EntityItem dropHeldItem() {
		if (heldItem != null) {
			if (!heldItem.onDrop(this)) return null;
			EntityItem e = heldItem.dropItemAt(world, x, y);
			heldItem = null;
			useTime = 0;
			return e;
		}
		return null;
	}
	
	@Override
	public void damage(int amount) {
		super.damage(amount);
		if (amount > 0) {
			Random r = world.getRandom();
			EntityParticle p;
			for (int i = 0; i < amount; i++) {
				p = new EntityParticle(world, x + r.nextFloat() * 0.75f, y + r.nextFloat() * (height - 0.25f), 0, 4.0f);
				p.push(world.getRandom().nextFloat() * 20.0f - 10.0f, 5.0f + r.nextFloat() * 5.0f);
				world.spawnParticle(p);
			}
			if (health <= 0 && coins > 0) {
				Globals.changeCoins(coins);
				p = new EntityParticle(world, x + r.nextFloat() * 0.75f, y + r.nextFloat() * (height - 0.25f), 2, 4.0f);
				p.push(world.getRandom().nextFloat() * 20.0f - 10.0f, 5.0f + r.nextFloat() * 5.0f);
				world.spawnParticle(p);
			}
		}
	}
	
	public void setOnScreen(boolean val) {
		this.onScreen = val;
	}
}
