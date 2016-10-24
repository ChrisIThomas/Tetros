package com.thomasci.tetros.shop;

import java.awt.Image;

import com.thomasci.tetros.entity.EntityDropItem;
import com.thomasci.tetros.item.Item;
import com.thomasci.tetros.world.World;

public class ShopItemItem extends ShopItem {
	private final Item item;
	private final String name;
	private final int price;
	
	public ShopItemItem(Item item, String name, int price) {
		this.item = item;
		this.name = name;
		this.price = price;
	}
	
	@Override
	public Image getSourceImage() {
		return items;
	}
	
	@Override
	public int getPrice() {
		return price;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean leaveShopAfterPurchase() {
		return true;
	}
	
	@Override
	public void purchase(World world) {
		world.dropPiece(new EntityDropItem(world, world.getPlayer().getX(), world.getHeight() - 1, item));
	}
	
	@Override
	public int getIconId() {
		return item.getIcon();
	}
}
