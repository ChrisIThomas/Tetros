package com.thomasci.tetros.entity;

import java.util.Random;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.world.World;

public class EntityCoinMachine extends Entity {
	private float coinTime, maxTime;
	private boolean doubled = false;
	
	public EntityCoinMachine(World world, float x, float y) {
		super(world, x, y);
		width = 1;
		height = 1;
		coinTime = 5.0f;
		maxTime = 5.0f;
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		coinTime -= dt;
		if (coinTime <= 0) {
			coinTime = maxTime;
			Globals.changeCoins(doubled ? 2 : 1);
			Random r = world.getRandom();
			EntityParticle p;
			for (int i = 0; i < (doubled ? 2 : 1); i++) {
				p = new EntityParticle(world,
						x + 0.25f + r.nextFloat() * 0.25f, y + 0.5f,
						2, 5.0f - r.nextFloat());
				p.push(r.nextFloat() * 10 - 5.0f, r.nextFloat() * 10 - 2.5f);
				world.spawnParticle(p);
			}
		}
	}
	
	public boolean speedUp() {
		if (maxTime <= 1.0f) return false;
		maxTime -= 1;
		return true;
	}
	
	public boolean doubleCoins() {
		if (doubled) return false;
		doubled = true;
		return true;
	}
}
