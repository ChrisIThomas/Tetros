package com.thomasci.tetros.item;

import com.thomasci.tetros.entity.EntityLiving;

public class ItemDrumstick extends Item {
	public ItemDrumstick(int id) {
		super(id, 3);
	}
	
	public void onUse(EntityLiving e) {
		e.heal(5);
		e.removeHeldItem();
	}
}
