package com.thomasci.tetros.shop;

import com.thomasci.tetros.state.StateBuy;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class ShopItemTileTemp extends ShopItemTile {
	public ShopItemTileTemp(Tile tile, String name, int price) {
		super(tile, name, price);
	}
	
	@Override
	public void purchase(World world) {
		super.purchase(world);
		StateBuy.removeShopItem(this);
	}
}
