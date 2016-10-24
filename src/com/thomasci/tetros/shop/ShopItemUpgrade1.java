package com.thomasci.tetros.shop;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class ShopItemUpgrade1 extends ShopItemTileTemp {
	public ShopItemUpgrade1(Tile tile, String name, int price) {
		super(tile, name, price);
	}
	
	@Override
	public void purchase(World world) {
		super.purchase(world);
		Globals.changeMaxCoins(1500);
	}
}
