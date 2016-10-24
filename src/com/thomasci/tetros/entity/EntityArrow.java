package com.thomasci.tetros.entity;

import com.thomasci.tetros.world.World;

public class EntityArrow extends Entity {
	private Entity shooter;
	
	public EntityArrow(World world, float x, float y, float vx, float vy, Entity e) {
		super(world, x, y);
		this.vx = vx;
		this.vy = vy;
		this.shooter = e;
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		vy /= 1.25f;
		Entity e = world.getEntityNear(x, y, 2.0f, EntityLiving.class, this, shooter);
		if (e != null) {
			e.damage(4);
			kill();
		} else if (this.onGround || this.vx == 0) kill();
	}
}
