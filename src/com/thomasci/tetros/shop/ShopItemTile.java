package com.thomasci.tetros.shop;

import com.thomasci.tetros.entity.EntityDropPiece;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class ShopItemTile extends ShopItem {
	private final Tile tile;
	private final String name;
	private final int price;
	
	public ShopItemTile(Tile tile, String name, int price) {
		this.tile = tile;
		this.name = name;
		this.price = price;
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
		world.dropPiece(new EntityDropPiece(world, world.getPlayer().getX(), world.getHeight() - 1, tile));
	}
	
	@Override
	public int getIconId() {
		return tile.getIcon();
	}
}
