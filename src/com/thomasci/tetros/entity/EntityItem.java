package com.thomasci.tetros.entity;

import com.thomasci.tetros.item.Item;
import com.thomasci.tetros.world.World;

public class EntityItem extends Entity {
	private Item item;
	
	public EntityItem(World world, float x, float y, Item item) {
		super(world, x, y);
		this.item = item;
	}
	
	public Item getItem() {
		return item;
	}
}
