package com.thomasci.tetros.item;

import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityCoinMachine;
import com.thomasci.tetros.entity.EntityLiving;

public class ItemMachineUpgrade extends Item {
	public ItemMachineUpgrade(int id) {
		super(id, 4);
	}
	
	public void onUse(EntityLiving e) {
		Entity e2 = e.getWorld().getEntityNear(e.getX(), e.getY(), 0.5f, EntityCoinMachine.class);
		if (e2 != null) {
			if (((EntityCoinMachine) e2).speedUp()) e.removeHeldItem();
		}
	}
}
