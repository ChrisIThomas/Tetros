package com.thomasci.tetros.item;

import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityLiving;

public class ItemClub extends Item {
	public ItemClub(int id) {
		super(id, 0);
	}
	
	public void onUse(EntityLiving e) {
		Entity ent = e.getWorld().getEntityAt(
				e.getX() + (e.isFacingRight() ? 0.75f : -0.75f),
				e.getY() + 1,
				1, 2,
				EntityLiving.class, e);
		if (ent != null) {
			ent.damage(2);
		}
	}
}
