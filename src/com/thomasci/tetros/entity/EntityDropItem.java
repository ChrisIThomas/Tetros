package com.thomasci.tetros.entity;

import com.thomasci.tetros.item.Item;
import com.thomasci.tetros.screen.GameSounds;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class EntityDropItem extends EntityDropPiece {
	private final Item item;
	
	public EntityDropItem(World world, float x, float y, Item item) {
		super(world, x, y, Tile.PACKAGE);
		this.item = item;
	}
	
	protected void onDrop() {
		world.spawnEntity(new EntityItem(world, x, y, item));
		GameSounds.getSound("place").play();
	}
}
