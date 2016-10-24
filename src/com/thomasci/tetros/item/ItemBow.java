package com.thomasci.tetros.item;

import com.thomasci.tetros.entity.EntityArrow;
import com.thomasci.tetros.entity.EntityLiving;
import com.thomasci.tetros.screen.GameSounds;

public class ItemBow extends Item {
	public ItemBow(int id) {
		super(id, 5);
	}
	
	public void onUse(EntityLiving e) {
		e.getWorld().spawnEntity(new EntityArrow(e.getWorld(), e.getX(), e.getY() + 1, e.isFacingRight() ? 20 : -20, 5, e));
		e.setUseTime(0.5f);
		GameSounds.getSound("dig").play();
	}
}
