package com.thomasci.tetros.item;

import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityCoinMachine;
import com.thomasci.tetros.entity.EntityLiving;

public class ItemMachineUpgrade2 extends Item {
	public ItemMachineUpgrade2(int id) {
		super(id, 6);
	}
	
	public void onUse(EntityLiving e) {
		Entity e2 = e.getWorld().getEntityNear(e.getX(), e.getY(), 0.5f, EntityCoinMachine.class);
		if (e2 != null) {
			if (((EntityCoinMachine) e2).doubleCoins()) e.removeHeldItem();
		}
	}
}
